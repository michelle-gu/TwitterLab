package com.khoros.twitter.models;

public class User {

    private String twitterHandle;
    private String name;
    private String profileImageUrl;

    public User(String twitterHandle, String name, String profileImageUrl) {
        this.twitterHandle = twitterHandle;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public void setTwitterHandle(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    public String getTwitterHandle() {
        return twitterHandle;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }


}
