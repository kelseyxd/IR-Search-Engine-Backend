//  The controller serves as an intermediary between the view and the model.
//  It handles user requests, invokes appropriate business logic, and updates the model based on user input
//  In a Spring MVC application, controllers are typically responsible for mapping HTTP requests to specific methods or endpoints.

package com.example.springbootelasticsearchexample.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.springbootelasticsearchexample.entity.Product;
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

    @GetMapping("/matchAllProducts") // match all results in Product index
    public List<Product> matchAllProducts() throws IOException { // return the list of Products
        SearchResponse<Product> searchResponse =  elasticSearchService.matchAllProductsServices();
        System.out.println(searchResponse.hits().hits().toString());

        List<Hit<Product>> listOfHits= searchResponse.hits().hits();
        List<Product> listOfProducts = new ArrayList<>();
        for(Hit<Product> hit : listOfHits){ // iterate through listOfHits to append each of the product to listOfProducts
            listOfProducts.add(hit.source()); // hit.source has the product entity details
        }
        return listOfProducts;
    }

    @GetMapping("/matchAllProducts/{fieldValue}")
    public List<Product> matchAllProductsWithName(@PathVariable String fieldValue) throws IOException {
        SearchResponse<Product> searchResponse =  elasticSearchService.matchProductsWithName(fieldValue);
        System.out.println(searchResponse.hits().hits().toString());

        List<Hit<Product>> listOfHits= searchResponse.hits().hits();
        List<Product> listOfProducts = new ArrayList<>();
        for(Hit<Product> hit : listOfHits){
            System.out.println("Score: " + hit.score()); // Print the hit score
//            System.out.println(hit.source().toString()); // Print the product
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }

    @GetMapping("/matchProductId/{fieldValue}")
    public List<Product> matchAllProductsWithId(@PathVariable String fieldValue) throws IOException {
        SearchResponse<Product> searchResponse =  elasticSearchService.matchProductsWithId(fieldValue);
        System.out.println(searchResponse.hits().hits().toString());

        List<Hit<Product>> listOfHits= searchResponse.hits().hits();
        List<Product> listOfProducts = new ArrayList<>();
        for(Hit<Product> hit : listOfHits){
            System.out.println("Score: " + hit.score()); // Print the hit score
//            System.out.println(hit.source().toString()); // Print the product
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }

//    @GetMapping("/matchAllCountries/{fieldValue}")
//    public List<Product> matchAllProductsWithCountry(@PathVariable String fieldValue) throws IOException {
//        SearchResponse<Product> searchResponse =  elasticSearchService.matchProductsWithCountry(fieldValue);
//        System.out.println(searchResponse.hits().hits().toString());
//
//        List<Hit<Product>> listOfHits= searchResponse.hits().hits();
//        List<Product> listOfProducts = new ArrayList<>();
//        for(Hit<Product> hit : listOfHits){
//            System.out.println("Score: " + hit.score()); // Print the hit score
//            System.out.println(hit.source().toString()); // Print the product
//            listOfProducts.add(hit.source());
//        }
//        return listOfProducts;
//    }
}