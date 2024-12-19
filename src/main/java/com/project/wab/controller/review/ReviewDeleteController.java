package com.project.wab.controller.review;

import com.project.wab.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewDeleteController {
    private final ReviewService reviewService;

    @PostMapping("/delete")
    public String delete(final Long reviewId, final Long productId) {
        reviewService.deleteReview(reviewId);
        return "redirect:/review/list?productId=" + productId;
    }
}

