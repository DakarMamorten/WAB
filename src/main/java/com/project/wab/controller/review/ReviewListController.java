package com.project.wab.controller.review;

import com.project.wab.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewListController {
    private final ReviewService reviewService;

    @GetMapping("/list")
    public String list(Model model, final Long productId) {
        model.addAttribute("reviews", reviewService.getReviewsByProduct(productId));
        return "review/list";
    }
}
