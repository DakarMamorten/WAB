package com.project.wab.controller.order;

import com.project.wab.dto.AddressDTO;
import com.project.wab.mapper.AddressMapper;
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

    private final AddressMapper addressMapper;
    private final OrderService orderService;

    @GetMapping
    public String showCheckoutForm(Model model) {
        model.addAttribute("addressDTO", new AddressDTO());
        return "/checkout/form";
    }

    @PostMapping
    public String processCheckout(@ModelAttribute @Valid AddressDTO addressDTO,
                                  BindingResult bindingResult,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  RedirectAttributes redirectAttributes) {
        var cartToken = WebUtil.checkToken(request);
        if (bindingResult.hasErrors()) {
            return "/checkout/form";
        }

        var address = addressMapper.addressDtoToAddress(addressDTO);
        var order = orderService.placeOrder(cartToken, address);
        response.addCookie(WebUtil.removeCookie());
        redirectAttributes.addFlashAttribute("orderId", order.getId().toString());

        return "redirect:/checkout/success";
    }

    @GetMapping("/success")
    public String showSuccessPage(Model model) {
        var orderId = (String) model.getAttribute("orderId");
        var dorderDto = orderService.getOderViewById(UUID.fromString(Objects.requireNonNull(orderId)));
        model.addAttribute("order", dorderDto);
        return "/checkout/success";
    }


}
