package com.example.springbootelasticsearchexample.repo;

import com.example.springbootelasticsearchexample.entity.Review;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

// Params accepts a product of type Product, and ID of product of type Integer
public interface ReviewRepo extends ElasticsearchRepository<Review,Integer> { // by extendind the ElasticsearchRepository, ProductRepo inherits various methods for performing CRUD on Elasticsearch documents

}
