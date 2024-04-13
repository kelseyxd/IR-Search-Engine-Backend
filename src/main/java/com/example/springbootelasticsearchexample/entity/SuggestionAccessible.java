package com.example.springbootelasticsearchexample.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SuggestionAccessible {
    @JsonProperty("length")
    private int length;
    @JsonProperty("offset")
    private int offset;
    @JsonProperty("text")
    private String text;
    @JsonProperty("options")
    private List<Option> options;

    public static class Option {
        @JsonProperty("text")
        private String text;
        @JsonProperty("score")
        private float score;
        @JsonProperty("freq")
        private int freq;
    }
}
