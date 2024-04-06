package com.example.springbootelasticsearchexample.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.springbootelasticsearchexample.util.ElasticSearchUtil;
import com.example.springbootelasticsearchexample.entity.Product;
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
        SearchResponse<Map> searchResponse = elasticsearchClient.search(s->s.query(supplier.get()),Map.class); // supplier.get(): gets the query and pass it into the search method of the elasticsearch // each search hit is represented as a Map object
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }
    //matchAllProducts video content

    public SearchResponse<Product> matchAllProductsServices(int page, int size) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.supplier();
        // Calculate the "from" parameter based on the page number and size
        int from = (page - 1) * size;

        SearchResponse<Product> searchResponse = elasticsearchClient.search(s -> s
                        .index("products")
                        .query(supplier.get())
                        .from(from)  // Set the offset
                        .size(size), // Set the size (number of results to return)
                Product.class);
        System.out.println("elasticsearch query is " + supplier.get().toString());
        return searchResponse;
    }


    public SearchResponse<Product> matchProductsWithName(String fieldValue, int page, int size) throws IOException { // return only products with matching names // pass in the name as argument
        Supplier<Query> supplier  = ElasticSearchUtil.supplierWithNameField(fieldValue);
        int from = (page - 1) * size;

        SearchResponse<Product> searchResponse = elasticsearchClient.search(s -> s
                        .index("products")
                        .query(supplier.get())
                        .from(from)  // Set the offset
                        .size(size), // Set the size (number of results to return)
                Product.class);
//        SearchResponse<Product> searchResponse = elasticsearchClient.search(s->s.index("products").query(supplier.get()), Product.class); // size specifies the number of results to be returned
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }

    public SearchResponse<Product> matchProductsWithId(String fieldValue) throws IOException { // return only products with matching names // pass in the name as argument
        Supplier<Query> supplier  = ElasticSearchUtil.supplierWithIdField(fieldValue);
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s->s.index("products").query(supplier.get()), Product.class); // size specifies the number of results to be returned
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }

    public SearchResponse<Product> matchProductsWithCategory(String fieldValue) throws IOException { // return only products with matching names // pass in the name as argument
        Supplier<Query> supplier  = ElasticSearchUtil.supplierWithCategoryField(fieldValue);
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s->s.index("products").query(supplier.get()), Product.class);
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }
}
