package com.example.springbootelasticsearchexample.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Review {
    @Id
    private String id;
    private double rating;
    private String title;

    private String date;

    private String country;
    private String content;
    private int sentiment;
}
