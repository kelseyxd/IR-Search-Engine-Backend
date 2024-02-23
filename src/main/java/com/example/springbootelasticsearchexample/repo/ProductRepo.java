package com.example.springbootelasticsearchexample.repo;

import com.example.springbootelasticsearchexample.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

// The arguments are the entity(Product) and the type of id (integer)
public interface ProductRepo extends ElasticsearchRepository<Product,Integer>{ // by extendind the ElasticsearchRepository, ProductRepo inherits various methods for performing CRUD on Elasticsearch documents

}
