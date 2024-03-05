package com.example.springbootelasticsearchexample.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data //includes @ToString, @Getter, @Setter, @EqualsAndHashCode and @RequiredArgsConstructor

@Document(indexName = "reviews") // each of the Product object initiated is a document in the products index
@JsonIgnoreProperties(ignoreUnknown = true) // ignore any additional fields like "_class"
// model in MVC pattern
public class Review {
    private int id;
    private String name;
    private int rating;
    private String review;
    private String date;
    private String country;
}
