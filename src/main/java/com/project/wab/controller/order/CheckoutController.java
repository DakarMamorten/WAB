package com.project.wab.controller.order;

import com.project.wab.domain.Address;
import com.project.wab.domain.order.Order;
import com.project.wab.dto.AddressDTO;
import com.project.wab.dto.AddressMapper;
import com.project.wab.service.user.OrderService;
import com.project.wab.utils.WebUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        String cartToken = WebUtil.checkToken(request);
        if (bindingResult.hasErrors()) {
            return "/checkout/form";
        }

        Address address = addressMapper.mapToAddress(addressDTO);
        Order order = orderService.placeOrder(cartToken, address);
        orderService.removeCartCookie(response);
        redirectAttributes.addFlashAttribute("orderId", order.getId());
        redirectAttributes.addFlashAttribute("orderState", order.getState().toString());
        redirectAttributes.addFlashAttribute("orderStatus", order.getPaymentState().toString());
        redirectAttributes.addFlashAttribute("shipmentState", order.getShipmentState().toString());
        redirectAttributes.addFlashAttribute("totalPrice", order.getTotalPrice());
        redirectAttributes.addFlashAttribute("order", order);

        return "redirect:/checkout/success";
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "/checkout/success";
    }


}
