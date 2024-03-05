package com.example.springbootelasticsearchexample.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.springbootelasticsearchexample.util.ElasticSearchUtil;
import com.example.springbootelasticsearchexample.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class ElasticSearchService {

    @Autowired
    private ElasticsearchClient  elasticsearchClient; // elastic search method

    public SearchResponse<Map> matchAllServices() throws IOException {
        Supplier<Query> supplier  = ElasticSearchUtil.supplier();
        SearchResponse<Map> searchResponse = elasticsearchClient.search(s->s.query(supplier.get()),Map.class); // supplier.get(): gets the query and pass it into the search method of the elasticseach // each search hit is represented as a Map object
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }
    //matchAllProducts video content

    public SearchResponse<Review> matchAllProductsServices() throws IOException { // return Product objects
        Supplier<Query> supplier  = ElasticSearchUtil.supplier();
        SearchResponse<Review> searchResponse = elasticsearchClient.search(s->s.index("reviews").query(supplier.get()), Review.class);
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }

    public SearchResponse<Review> matchProductsWithName(String fieldValue) throws IOException { // return only products with matching names // pass in the name as argument
        Supplier<Query> supplier  = ElasticSearchUtil.supplierWithNameField(fieldValue);
        SearchResponse<Review> searchResponse = elasticsearchClient.search(s->s.index("reviews").query(supplier.get()), Review.class);
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }

    public SearchResponse<Review> matchProductsWithCountry(String fieldValue) throws IOException { // return only products with matching names // pass in the name as argument
        Supplier<Query> supplier  = ElasticSearchUtil.supplierWithCountryField(fieldValue);
        SearchResponse<Review> searchResponse = elasticsearchClient.search(s->s.index("reviews").query(supplier.get()), Review.class);
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }
}
