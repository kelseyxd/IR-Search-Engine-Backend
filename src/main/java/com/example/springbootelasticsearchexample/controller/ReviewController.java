//  The controller serves as an intermediary between the view and the model.
//  It handles user requests, invokes appropriate business logic, and updates the model based on user input
//  In a Spring MVC application, controllers are typically responsible for mapping HTTP requests to specific methods or endpoints.

package com.example.springbootelasticsearchexample.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.springbootelasticsearchexample.entity.CustomSearchResponse;
import com.example.springbootelasticsearchexample.entity.Product;
import com.example.springbootelasticsearchexample.entity.SuggestionAccessible;
import com.example.springbootelasticsearchexample.service.ElasticSearchService;
import com.example.springbootelasticsearchexample.service.ProductService;
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
    private ProductService productService;

    @Autowired
    private ElasticSearchService elasticSearchService;

    @GetMapping("/findAll") // It specifies that the method findAll() should be called when a GET request is made to this endpoint
    Iterable<Product> findAll(){
        return productService.getProducts();
    }

    @PostMapping("/insert")
    public Product insertProduct(@RequestBody Product product){
        return productService.insertProduct(product);
    }

    @PostMapping("/insertList")
    public List<Product> insertProductList(@RequestBody ArrayList<Product> products){
        return productService.insertProductList(products);
    }

    @GetMapping("/matchAll") // match all results in ALL index
    public String matchAll() throws IOException {
        SearchResponse<Map> searchResponse =  elasticSearchService.matchAllServices();
        System.out.println(searchResponse.hits().hits().toString());
        return searchResponse.hits().hits().toString();
    }

    @GetMapping("/matchAllProducts")
    public List<Product> matchAllProducts(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size) throws IOException {
        SearchResponse<Product> searchResponse = elasticSearchService.matchAllProductsServices(page, size);
        System.out.println(searchResponse.hits().hits().toString());

        List<Hit<Product>> listOfHits = searchResponse.hits().hits();
        List<Product> listOfReviews = new ArrayList<>();
        for (Hit<Product> hit : listOfHits) {
            listOfReviews.add(hit.source());
        }
        return listOfReviews;
    }


    @GetMapping("/matchAllProducts/{fieldValue}")
    public CustomSearchResponse matchAllProductsWithName(@PathVariable String fieldValue,
                                                        @RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "10") int size) throws IOException {
        // Start timing
        long startTime = System.currentTimeMillis();

        // Execute the search
        SearchResponse<Product> searchResponse = elasticSearchService.matchProductsWithName(fieldValue, page, size);

        // Stop timing
        long endTime = System.currentTimeMillis();
        long queryTime = endTime - startTime;
        System.out.println("Query products(name) execution time: " + queryTime + "ms");

//        System.out.println(searchResponse.hits().hits().toString());
        System.out.println("Suggestions: " + searchResponse.suggest().get("product-name-suggestion").toString());

        List<Hit<Product>> listOfHits= searchResponse.hits().hits();
        List<Product> listOfProducts = new ArrayList<>();
        for(Hit<Product> hit : listOfHits){
            System.out.println(hit.source().toString()); // Print the product
            System.out.println("Score: " + hit.score()); // Print the hit score
            listOfProducts.add(hit.source());
        }

        List<SuggestionAccessible> suggestions = elasticSearchService.getSuggestionsForName(searchResponse);

        return new CustomSearchResponse(listOfProducts, suggestions);
    }

    @GetMapping("/matchProductId/{fieldValue}")
    public List<Product> matchAllProductsWithId(@PathVariable String fieldValue) throws IOException {
        // Start timing
        long startTime = System.currentTimeMillis();

        SearchResponse<Product> searchResponse =  elasticSearchService.matchProductsWithId(fieldValue);
//        System.out.println(searchResponse.hits().hits().toString());

        // Stop timing
        long endTime = System.currentTimeMillis();
        long queryTime = endTime - startTime;
        System.out.println("Query product ID execution time: " + queryTime + "ms");

        List<Hit<Product>> listOfHits= searchResponse.hits().hits();
        List<Product> listOfProducts = new ArrayList<>();
        for(Hit<Product> hit : listOfHits){
            System.out.println(hit.source().toString()); // Print the product
            System.out.println("Score: " + hit.score()); // Print the hit score
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }

    @GetMapping("/matchProductCat/{fieldValue}")
    public List<Product> matchProductsWithCategory(@PathVariable String fieldValue,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "10") int size) throws IOException {

        // Start timing
        long startTime = System.currentTimeMillis();
        SearchResponse<Product> searchResponse =  elasticSearchService.matchProductsWithCategory(fieldValue, page, size);
//        System.out.println(searchResponse.hits().hits().toString());
        // Stop timing
        long endTime = System.currentTimeMillis();
        long queryTime = endTime - startTime;
        System.out.println("Query products (category) execution time: " + queryTime + "ms");

        List<Hit<Product>> listOfHits= searchResponse.hits().hits();
        List<Product> listOfProducts = new ArrayList<>();
        for(Hit<Product> hit : listOfHits){
            System.out.println(hit.source().toString()); // Print the product
            System.out.println("Score: " + hit.score()); // Print the hit score
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }
}