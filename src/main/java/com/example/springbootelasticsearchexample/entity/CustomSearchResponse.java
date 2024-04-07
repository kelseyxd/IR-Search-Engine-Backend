package com.example.springbootelasticsearchexample.entity;

import java.util.List;

public class CustomSearchResponse {
    private List<Product> products;
    private List<SuggestionAccessible> suggestions;

    public CustomSearchResponse(List<Product> products, List<SuggestionAccessible> suggestions) {
        this.products = products;
        this.suggestions = suggestions;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<SuggestionAccessible> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<SuggestionAccessible> suggestions) {
        this.suggestions = suggestions;
    }
}

