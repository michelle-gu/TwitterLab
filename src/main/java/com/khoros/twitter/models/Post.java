package com.khoros.twitter.models;

import java.util.Date;

public class Post {

    private String message;
    private User user;
    private Date createdAt;

    public Post(String message, User user, Date createdAt) {
        this.message = message;
        this.user = user;
        this.createdAt = createdAt;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

}
