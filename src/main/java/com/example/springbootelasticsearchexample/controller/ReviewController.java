//  The controller serves as an intermediary between the view and the model.
//  It handles user requests, invokes appropriate business logic, and updates the model based on user input
//  In a Spring MVC application, controllers are typically responsible for mapping HTTP requests to specific methods or endpoints.

package com.example.springbootelasticsearchexample.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.springbootelasticsearchexample.entity.Review;
import com.example.springbootelasticsearchexample.service.ElasticSearchService;
import com.example.springbootelasticsearchexample.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// controller layer is generally tied to the Spring API
@RestController
@RequestMapping("/apis") // specify the base URL path ("/apis") for all the HTTP endpoints defined in this controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ElasticSearchService elasticSearchService;

    @GetMapping("/findAll") // It specifies that the method findAll() should be called when a GET request is made to this endpoint
    Iterable<Review> findAll(){
        return reviewService.getReviews();
    }

    @PostMapping("/insert")
    public Review insertReview(@RequestBody Review review){
        return reviewService.insertReview(review);
    }

    @PostMapping("/insertList")
    public List<Review> insertReviewList(@RequestBody ArrayList<Review> reviews){
        return reviewService.insertReviewList(reviews);
    }

    @GetMapping("/matchAll") // match all results in ALL index
    public String matchAll() throws IOException {
        SearchResponse<Map> searchResponse =  elasticSearchService.matchAllServices();
        System.out.println(searchResponse.hits().hits().toString());
        return searchResponse.hits().hits().toString();
    }

    @GetMapping("/matchAllProducts") // match all results in Product index
    public List<Review> matchAllProducts() throws IOException { // return the list of Products
        SearchResponse<Review> searchResponse =  elasticSearchService.matchAllProductsServices();
        System.out.println(searchResponse.hits().hits().toString());

        List<Hit<Review>> listOfHits= searchResponse.hits().hits();
        List<Review> listOfReviews = new ArrayList<>();
        for(Hit<Review> hit : listOfHits){ // iterate through listOfHits to append each of the product to listOfProducts
            listOfReviews.add(hit.source()); // hit.source has the product entity details
        }
        return listOfReviews;
    }

    @GetMapping("/matchAllProducts/{fieldValue}")
    public List<Review> matchAllProductsWithName(@PathVariable String fieldValue) throws IOException {
        SearchResponse<Review> searchResponse =  elasticSearchService.matchProductsWithName(fieldValue);
        System.out.println(searchResponse.hits().hits().toString());

        List<Hit<Review>> listOfHits= searchResponse.hits().hits();
        List<Review> listOfReviews = new ArrayList<>();
        for(Hit<Review> hit : listOfHits){
            System.out.println("Score: " + hit.score()); // Print the hit score
            System.out.println(hit.source().toString()); // Print the product
            listOfReviews.add(hit.source());
        }
        return listOfReviews;
    }

    @GetMapping("/matchAllCountries/{fieldValue}")
    public List<Review> matchAllProductsWithCountry(@PathVariable String fieldValue) throws IOException {
        SearchResponse<Review> searchResponse =  elasticSearchService.matchProductsWithCountry(fieldValue);
        System.out.println(searchResponse.hits().hits().toString());

        List<Hit<Review>> listOfHits= searchResponse.hits().hits();
        List<Review> listOfReviews = new ArrayList<>();
        for(Hit<Review> hit : listOfHits){
            System.out.println("Score: " + hit.score()); // Print the hit score
            System.out.println(hit.source().toString()); // Print the product
            listOfReviews.add(hit.source());
        }
        return listOfReviews;
    }
}