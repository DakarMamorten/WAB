package com.project.wab.controller;

import com.project.wab.domain.Cart;
import com.project.wab.domain.User;
import com.project.wab.service.CartItemService;
import com.project.wab.service.CartService;
import com.project.wab.service.ProductService;
import com.project.wab.utils.WebUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class UserIndexController {
    public static final String USER_INDEX = "user_index";
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final CartService cartService;

    @GetMapping("/")
    public String userIndex(@CurrentSecurityContext SecurityContext context,
                            Model model,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            RedirectAttributes redirectAttributes) {
        var products = productService.getAllProducts();
        model.addAttribute("products", products);

        Object principal = context.getAuthentication().getPrincipal();

        if (principal.equals("anonymousUser")) {
            String cartToken = WebUtil.checkToken(request);
            if (cartToken == null) {
                model.addAttribute("cartSize", 0);
                return USER_INDEX;
            } else {
                Integer cartTotal = cartItemService.totalProductByCartId(UUID.fromString(cartToken));
                model.addAttribute("cartSize", cartTotal);
                redirectAttributes.addFlashAttribute("cartSize");
                return  USER_INDEX;
            }
        } else {
            String cartToken = WebUtil.checkToken(request);
            User currentUser = (User) context.getAuthentication().getPrincipal();
            String tokenFromDb = cartService.findCartIdByUserID(currentUser.getId());
            if (cartToken == null) {
                Cart cart = cartService.findCartByUserID(currentUser.getId());
                if (cart == null) {
                    model.addAttribute("cartSize", 0);
                    return USER_INDEX;
                }
                Integer cartTotal = cartItemService.totalProductByUserId(currentUser.getId());
                model.addAttribute("cartSize", cartTotal);
            } else if (!cartToken.equals(tokenFromDb)) {
                //merge 2 carts
                String newCartToken = cartItemService.mergeCart(cartToken, currentUser.getId());
                Cookie cookie = WebUtil.populateCookie(newCartToken);
                response.addCookie(cookie);
                Integer cartTotal = cartItemService.totalProductByUserId(currentUser.getId());
                model.addAttribute("cartSize", cartTotal);
            }
        }

        return USER_INDEX;
    }
}
