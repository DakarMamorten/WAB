package com.project.wab.controller.review;

import com.project.wab.domain.Product;
import com.project.wab.domain.Review;
import com.project.wab.domain.User;
import com.project.wab.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

/**
 * @author "Vladyslav Paun"
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewAddController {
    private final ReviewService reviewService;

    @PostMapping("/add")
    public String add(final Long productId, final Long userId, final String comment, final int rating) {
        Review review = new Review(null, new Product(productId, null, null, null, null, null), new User(userId, null, null, null, null, null), comment, rating, LocalDateTime.now());
        reviewService.addReview(review);
        return "redirect:/review/list?productId=" + productId;
    }
}


