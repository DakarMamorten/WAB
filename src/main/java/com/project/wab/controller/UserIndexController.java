package com.project.wab.controller;

import com.project.wab.domain.User;
import com.project.wab.service.CartService;
import com.project.wab.service.ProductService;
import com.project.wab.utils.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class UserIndexController {
    private final ProductService productService;
    private final CartService cartService;

    @GetMapping("/")
    public String userIndex(@CurrentSecurityContext SecurityContext context,
                            Model model,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        var products = productService.getAllProducts();
        model.addAttribute("products", products);

        Object principal = context.getAuthentication().getPrincipal();
        var cartToken = WebUtil.checkToken(request);
        var cartId = cartToken == null ? null : UUID.fromString(cartToken);

        if ("anonymousUser".equals(principal)) {
            if (cartToken == null) {
                model.addAttribute("cartSize", 0);
            } else {
                final Map<String, Object> map = cartService.mergeAndDeleteCart(cartId, null);
                model.addAttribute("cartSize", map.get("p_total"));
            }
        } else {
            var currentUser = (User) context.getAuthentication().getPrincipal();
            var map = cartService.mergeAndDeleteCart(cartId, currentUser.getId());
            model.addAttribute("cartSize", map.get("p_total"));
            var pCartId = map.get("p_cart_id");
            if (pCartId != null) {
                response.addCookie(WebUtil.populateCookie(pCartId.toString()));
            }
        }
        return "user_index";
    }
}
