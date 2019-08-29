package com.khoros.twitter.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Post {

    private String message;
    private User user;
    private Date createdAt;

    public Post() {
        // Jackson deserialization
    }

    public Post(String message, User user, Date createdAt) {
        this.message = message;
        this.user = user;
        this.createdAt = createdAt;
    }

    @JsonProperty
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @JsonProperty
    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty
    public User getUser() {
        return user;
    }

    @JsonProperty
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty
    public Date getCreatedAt() {
        return createdAt;
    }

}
