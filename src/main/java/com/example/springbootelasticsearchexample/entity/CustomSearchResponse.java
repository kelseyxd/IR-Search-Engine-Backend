package com.example.springbootelasticsearchexample.entity;

import java.util.List;

public class CustomSearchResponse {
    private List<Product> products;
    private List<SuggestionResponse> suggestions;

    public CustomSearchResponse(List<Product> products, List<SuggestionResponse> suggestions) {
        this.products = products;
        this.suggestions = suggestions;
    }

    // Getters and setters

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<SuggestionResponse> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<SuggestionResponse> suggestions) {
        this.suggestions = suggestions;
    }
}

