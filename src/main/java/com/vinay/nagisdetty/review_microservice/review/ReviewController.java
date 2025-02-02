package com.vinay.nagisdetty.review_microservice.review;

import com.vinay.nagisdetty.review_microservice.review.messaging.ReviewmessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;
    private ReviewmessageProducer reviewmessageProducer;


    public ReviewController(ReviewService reviewService,ReviewmessageProducer reviewmessageProducer) {
        this.reviewService = reviewService;
        this.reviewmessageProducer = reviewmessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){
        return new ResponseEntity<>(reviewService.getAllReviews(companyId),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId,
                                            @RequestBody Review review){
            boolean isReviewSaved = reviewService.addReview(companyId, review);
            if (isReviewSaved) {
                reviewmessageProducer.send(review);
                return new ResponseEntity<>("Review Added Successfully",
                        HttpStatus.OK);
            }
            else
                return new ResponseEntity<>("Review Not Saved",
                        HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(
                                            @PathVariable Long reviewId){
        return new ResponseEntity<>(reviewService.getReview( reviewId),
                                    HttpStatus.OK);

    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(
                                               @PathVariable Long reviewId,
                                               @RequestBody Review review){
        boolean isReviewUpdated = reviewService.updateReview(reviewId, review);
        if (isReviewUpdated)
            return new ResponseEntity<>("Review updated successfully",
                    HttpStatus.OK);
        else
            return new ResponseEntity<>("Review not updated",
                    HttpStatus.NOT_FOUND);


    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
        boolean isReviewDeleted = reviewService.deleteReview(reviewId);
        if (isReviewDeleted)
            return new ResponseEntity<>("Review deleted successfully",
                    HttpStatus.OK);
        else
            return new ResponseEntity<>("Review not deleted",
                    HttpStatus.NOT_FOUND);
    }
    @GetMapping("/averagerating")
    public Double getAverageRating(@RequestParam Long companyId){
        List<Review> reviews = reviewService.getAllReviews(companyId);
        return reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }

}
