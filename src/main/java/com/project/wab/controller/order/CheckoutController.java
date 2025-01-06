package com.project.wab.controller.order;

import com.project.wab.dto.AddressDTO;
import com.project.wab.service.AddressService;
import com.project.wab.service.CheckoutService;
import com.project.wab.service.ReferenceBookService;
import com.project.wab.service.user.OrderService;
import com.project.wab.utils.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import java.util.UUID;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final OrderService orderService;
    private final ReferenceBookService referenceBookService;
    private final AddressService addressService;
    private final CheckoutService checkoutService;

    @GetMapping
    public String showCheckoutForm(Model model, HttpServletRequest request) {
        model.addAttribute("addressDTO", new AddressDTO());
        var token = WebUtil.checkToken(request);
        referenceBookService.getReferenceBookForCheckout(model, token);
        return "/checkout/form";
    }

    @PostMapping
    public String processCheckout(@ModelAttribute @Valid AddressDTO addressDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "/checkout/form";
        }
        var address = addressService.save(addressDTO);
        redirectAttributes.addFlashAttribute("addressId", address.getId());
        return "redirect:/checkout/pre-view";

    }

    @GetMapping("/pre-view")
    public String showPreViewForm(Model model, HttpServletRequest request) {
        var cartId = WebUtil.checkToken(request);
        checkoutService.preView(cartId, model);
        return "/checkout/pre-view";
    }

    @PostMapping("/pre-view")
    public String processPreView(Long addressId,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        var cartToken = WebUtil.checkToken(request);
        var order = orderService.placeOrder(cartToken, addressId);
        redirectAttributes.addAttribute("orderId", order.getId().toString());

        return "redirect:/checkout/success";
    }

    @GetMapping("/success")
    public String showSuccessPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        String orderId = request.getParameter("orderId");
        var dorderDto = orderService.getOderViewById(UUID.fromString(Objects.requireNonNull(orderId)));
        model.addAttribute("order", dorderDto);

        response.addCookie(WebUtil.removeCookie());

        return "/checkout/success";
    }

}
