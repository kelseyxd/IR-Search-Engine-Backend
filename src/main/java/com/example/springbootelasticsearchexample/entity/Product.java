package com.example.springbootelasticsearchexample.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Document(indexName = "products") // each of the Product object initiated is a document in the products index
@JsonIgnoreProperties(ignoreUnknown = true) // ignore any additional fields like "_class"
public class Product {
    private int id;
    private String name;
    private String description;
    private int quantity;
    private double price;
}