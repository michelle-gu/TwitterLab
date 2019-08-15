package com.khoros.twitter;

import com.khoros.twitter.core.TwitterLabFactory;
import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class TwitterLabConfiguration extends Configuration {

    @Valid
    @NotNull
    private TwitterLabFactory twitterLabFactory = new TwitterLabFactory();

    @JsonProperty("twitter")
    public TwitterLabFactory getTwitterLabFactory() {
        return twitterLabFactory;
    }

    @JsonProperty("twitter")
    public void setTwitterLabFactory(TwitterLabFactory factory) {
        this.twitterLabFactory = factory;
    }

}
