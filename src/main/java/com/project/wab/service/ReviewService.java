package com.project.wab.service;

import com.project.wab.domain.Review;
import com.project.wab.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> getReviewsByProduct(Long productId) {
        return reviewRepository.findAll()
                .stream()
                .filter(review -> review.getProduct().getId().equals(productId))
                .toList();
    }

    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public Review updateReview(Long reviewId, String comment, int rating) {
        Review review = getReviewById(reviewId);
        review.setComment(comment);
        review.setRating(rating);
        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}
