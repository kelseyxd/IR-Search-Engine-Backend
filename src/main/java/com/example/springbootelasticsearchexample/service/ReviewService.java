package com.example.springbootelasticsearchexample.service;

import com.example.springbootelasticsearchexample.entity.Review;
import com.example.springbootelasticsearchexample.repo.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// service layer is to be designed with business logic
@Service // The service layer sits between the controller layer and the repository layer
public class ReviewService {

    @Autowired // used to create an object called productRepo of class ProductRepo
    private ReviewRepo reviewRepo;

    public Iterable<Review> getReviews() {
        return reviewRepo.findAll(); // method from ElasticsearchRepository which ProductRepo extends from
    }

    public Review insertReview(Review review) {
        return reviewRepo.save(review);
    }

    public List<Review> insertReviewList(ArrayList<Review> reviews) {
        reviewRepo.saveAll(reviews);
        return reviews;
    }

//    how does the index get updated?
    public Review updateReview(Review review, int id) {
        Review review1 = reviewRepo.findById(id).get();
        review1.setRating(review.getRating());
        return review1;
    }

    public void deleteReview(int id ) {
        reviewRepo.deleteById(id);
    }
}