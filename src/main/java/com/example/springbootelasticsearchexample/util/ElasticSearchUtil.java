package com.example.springbootelasticsearchexample.util;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.val;

import java.util.function.Supplier;

public class ElasticSearchUtil {

    public static Supplier<Query> supplier(){ // A supplier is a functional interface in Java that provides a way of generating or supplying values. In this case, the supplier is designed to supply Elasticsearch queries.
        Supplier<Query> supplier = ()->Query.of(q->q.matchAll(matchAllQuery()));
        return supplier;
    }

    public static MatchAllQuery matchAllQuery(){ // returns a MatchAllQuery object. MatchAllQuery is a class provided by the Elasticsearch Java client library. It represents a "match all" query in Elasticsearch.

        val  matchAllQuery = new MatchAllQuery.Builder();
        return matchAllQuery.build(); // this is equivalent to running the match_all query on postman
    }

    public static Supplier<Query> supplierWithNameField(String fieldValue){
        Supplier<Query> supplier = ()->Query.of(q->q.match(matchQueryWithNameField(fieldValue)));
        return supplier;
    }

    public static MatchQuery matchQueryWithNameField(String fieldValue){
        val  matchQuery = new MatchQuery.Builder();
        return matchQuery.field("product_title").query(fieldValue).build(); // similar to postman
    }

    // Get by product ID
    public static Supplier<Query> supplierWithIdField(String fieldValue){
        Supplier<Query> supplier = ()->Query.of(q->q.match(matchQueryWithIdField(fieldValue)));
        return supplier;
    }

    public static MatchQuery matchQueryWithIdField(String fieldValue){
        val  matchQuery = new MatchQuery.Builder();
        return matchQuery.field("product_id").query(fieldValue).build();
    }


//    public static Supplier<Query> supplierWithCountryField(String fieldValue){
//        Supplier<Query> supplier = ()->Query.of(q->q.match(matchQueryWithCountryField(fieldValue)));
//        return supplier;
//    }
//    public static MatchQuery matchQueryWithCountryField(String fieldValue){
//        val  matchQuery = new MatchQuery.Builder();
//        return matchQuery.field("country").query(fieldValue).build(); // similar to postman
//    }
}