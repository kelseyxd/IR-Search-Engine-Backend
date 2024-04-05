package com.example.springbootelasticsearchexample.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data //includes @ToString, @Getter, @Setter, @EqualsAndHashCode and @RequiredArgsConstructor

@Document(indexName = "products") // each of the Product object initiated is a document in the products index
@JsonIgnoreProperties(ignoreUnknown = true) // ignore any additional fields like "_class"
// model in MVC pattern
public class Product {
    @Id
    private String product_id;
    private String product_title;
    private double price;
    private double rating;
    private int number_of_reviews;
    private boolean availability;

//    private String category;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Review> reviews;
}
