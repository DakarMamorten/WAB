package com.project.wab.controller.order;

import com.project.wab.dto.AddressDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {
    @GetMapping
    public String showCheckoutForm(Model model) {
        model.addAttribute("addressDTO", new AddressDTO());
        return "checkout/form";
    }

    @PostMapping
    public String processCheckout(@Valid @ModelAttribute("addressDTO") AddressDTO addressDTO,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "checkout/form";
        }

        System.out.println("Checkout data: " + addressDTO);
        return "redirect:/checkout/success";
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "checkout/success";
    }
}
