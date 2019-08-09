package com.khoros.twitter.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatusMessage {

    private String status;

    public StatusMessage() {
        // Jackson deserialization
    }

    public StatusMessage(String status) {
        this.status = status;
    }

    @JsonProperty
    public String getStatus() {
        return status;
    }

    @JsonProperty
    public void setStatus(String status) {
        this.status = status;
    }

}
