package com.example.springbootelasticsearchexample.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SuggestionResponse {
    @JsonProperty("length")
    private int length;
    @JsonProperty("offset")
    private int offset;
    @JsonProperty("text")
    private String text;
    @JsonProperty("options")
    private List<Option> options;

    public static class Option {
        private String text;
        private float score;
        private int freq;

        // Getters and setters

        @JsonProperty("text")
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @JsonProperty("score")
        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        @JsonProperty("freq")
        public int getFreq() {
            return freq;
        }

        public void setFreq(int freq) {
            this.freq = freq;
        }
    }
}
