package com.khoros.twitter.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

    private String text;

    public Message() {
        // Jackson deserialization
    }

    public Message(String text) {
        this.text = text;
    }

    @JsonProperty
    public String getText() {
        return text;
    }

    @JsonProperty
    public void setText(String text) {
        this.text = text;
    }

}
