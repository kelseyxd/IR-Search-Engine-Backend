package com.example.springbootelasticsearchexample.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.*;
import co.elastic.clients.json.JsonpMapper;
import com.example.springbootelasticsearchexample.entity.SuggestionAccessible;
import com.example.springbootelasticsearchexample.util.ElasticSearchUtil;
import com.example.springbootelasticsearchexample.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.stream.JsonGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
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

    public SearchResponse<Product> matchProductsWithName(String fieldValue, int page, int size) throws IOException {
        int from = (page - 1) * size;

        // Use the user input for both the query and the suggester
        Supplier<Query> supplier = ElasticSearchUtil.supplierWithNameField(fieldValue);

        Suggester suggester = Suggester.of(s -> s
                .text(fieldValue) // Use user input here
                .suggesters(Map.of(
                        "product-name-suggestion",
                        FieldSuggester.of(fs -> fs
                                .term(TermSuggester.of(ts -> ts
                                        .field("product_title") // Ensure this is the correct field for suggestions
                                ))
                        )
                ))
        );

        // Execute the search with the suggester
        SearchResponse<Product> searchResponse = executeSearch(supplier, suggester, "products", from, size);

//        // If no hits, perform fuzzy search
//        if (searchResponse.hits().total().value() == 0) {
//            Supplier<Query> fuzzySupplier = ElasticSearchUtil.supplierWithFuzzyNameField(fieldValue);
//            searchResponse = executeSearch(fuzzySupplier, suggester, "products", from, size);
//            System.out.println("fuzzy search response: " + searchResponse);
//        }

        System.out.println("Elasticsearch query is: " + supplier.get().toString());
        return searchResponse;
    }

    private SearchResponse<Product> executeSearch(Supplier<Query> querySupplier, Suggester suggester, String index, int from, int size) throws IOException {
        return elasticsearchClient.search(s -> s
                        .index(index)
                        .query(querySupplier.get())
                        .suggest(suggester)
                        .from(from)
                        .size(size),
                Product.class);
    }

    public List<SuggestionAccessible> getSuggestionsForName(SearchResponse<Product> searchResponse) throws IOException {
        List<Suggestion<Product>> listOfSuggestions = searchResponse.suggest().get("product-name-suggestion");
        List<SuggestionAccessible> listOfSuggestionAccessibles = new ArrayList<>();

        JsonpMapper mapper = elasticsearchClient._jsonpMapper();
        ObjectMapper objectMapper = new ObjectMapper(); // Used for JSON deserialization

        for (Suggestion<Product> suggestion : listOfSuggestions) { // extract suggestion for every word
            StringWriter writer = new StringWriter();
            try (JsonGenerator generator = mapper.jsonProvider().createGenerator(writer)) {
                mapper.serialize(suggestion, generator);
            }
            String result = writer.toString(); // convert suggestion object to string in order to access the json form

            // Deserialize the JSON string to a SuggestionAccessible object
            SuggestionAccessible suggestionAccessible = objectMapper.readValue(result, SuggestionAccessible.class);
            listOfSuggestionAccessibles.add(suggestionAccessible);
        }

        return listOfSuggestionAccessibles;
    }

    public SearchResponse<Product> matchProductsWithId(String fieldValue) throws IOException { // return only products with matching names // pass in the name as argument
        Supplier<Query> supplier  = ElasticSearchUtil.supplierWithIdField(fieldValue);
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s->s.index("products").query(supplier.get()), Product.class); // size specifies the number of results to be returned
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }

    public SearchResponse<Product> matchProductsWithCategory(String fieldValue, int page, int size) throws IOException { // return only products with matching names // pass in the name as argument
        Supplier<Query> supplier  = ElasticSearchUtil.supplierWithCategoryField(fieldValue);
        int from = (page - 1) * size;
        SearchResponse<Product> searchResponse = elasticsearchClient.search(
                s->s.index("products")
                    .query(supplier.get())
                    .from(from)
                    .size(size),
                Product.class);
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }
}
