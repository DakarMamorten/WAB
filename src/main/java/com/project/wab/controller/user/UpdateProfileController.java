package com.project.wab.controller.user;

import com.project.wab.dto.AddressDTO;
import com.project.wab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
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
@RequestMapping("/user")
@RequiredArgsConstructor
public class UpdateProfileController {
    private final UserService userService;
    @PostMapping("/update")
    public String updateAddress(@ModelAttribute("userUpdateDTO") AddressDTO addressDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "/user/profile";         }
        userService.updateAddress(addressDTO);
        return "redirect:/user/profile";
    }

    @GetMapping("/update")
    public String showUpdateForm(Model model, @CurrentSecurityContext SecurityContext context) {
        String email = context.getAuthentication().getName();
        AddressDTO addressDTO = userService.getAddressByUserEmail(email);
        if (addressDTO == null) {
            throw new RuntimeException("Address not found for user: " + email);
        }
        model.addAttribute("userUpdateDTO", addressDTO);
        return "user/profile";
    }
}
