package com.vinay.nagisdetty.review_microservice.review.impl;



import com.vinay.nagisdetty.review_microservice.review.Review;
import com.vinay.nagisdetty.review_microservice.review.ReviewRepository;
import com.vinay.nagisdetty.review_microservice.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository
                             ) {
        this.reviewRepository = reviewRepository;

    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        if (companyId != null && review != null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Review getReview( Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean updateReview( Long reviewId, Review updatedReview) {

        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null){
            review.setCompanyId(updatedReview.getCompanyId());
            review.setRating(updatedReview.getRating());
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            reviewRepository.save(review);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteReview( Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null){

            reviewRepository.delete(review);
            return true;
        } else {
            return false;
        }
    }
}
