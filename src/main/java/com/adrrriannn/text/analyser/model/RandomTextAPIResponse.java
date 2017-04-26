package com.adrrriannn.text.analyser.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by adrian on 24/04/2017.
 */
public class RandomTextAPIResponse {
    
    @JsonProperty("text_out")
    private String textOut;

    public String getTextOut () {
        return textOut;
    }
}
