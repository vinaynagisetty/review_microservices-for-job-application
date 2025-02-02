package com.vinay.nagisdetty.review_microservice.review.messaging;

import com.vinay.nagisdetty.review_microservice.review.dto.ReviewMessage;
import com.vinay.nagisdetty.review_microservice.review.Review;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
public class ReviewmessageProducer {
    private RabbitTemplate rabbitTemplate;

    public ReviewmessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void send(Review review) {
        ReviewMessage reviewMessage = new ReviewMessage();
        reviewMessage.setId(review.getId());
        reviewMessage.setTitle(review.getTitle());
        reviewMessage.setDescription(review.getDescription());
        reviewMessage.setRating(review.getRating());
        reviewMessage.setCompanyId(review.getCompanyId());
        rabbitTemplate.convertAndSend("company-review-queue", reviewMessage);
    }
}
