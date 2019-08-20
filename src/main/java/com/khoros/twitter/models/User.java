package com.khoros.twitter.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    private String twitterHandle;
    private String name;
    private String profileImageUrl;

    public User() {
        // Jackson deserialization
    }

    public User(String twitterHandle, String name, String profileImageUrl) {
        this.twitterHandle = twitterHandle;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    @JsonProperty
    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    @JsonProperty
    public String getTwitterHandle() {
        return twitterHandle;
    }

    @JsonProperty
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    @JsonProperty
    public String getProfileImageUrl() {
        return profileImageUrl;
    }


}
