package com.khoros.twitter.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import java.util.List;

// Provides services for getting home timeline and posting tweets
public class TwitterLabService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterLabService.class);

    private Twitter twitter;

    public TwitterLabService(Twitter twitter) {
        this.twitter = twitter;
    }


    public Status postTweet(String text) throws TwitterException {
        return twitter.updateStatus(text);
    }

    public List<Status> getTimeline() throws TwitterException {
        return twitter.getHomeTimeline();
    }

}
