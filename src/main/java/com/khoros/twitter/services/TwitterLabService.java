package com.khoros.twitter.services;

import com.khoros.twitter.TwitterLabConfiguration;
import com.khoros.twitter.core.Message;
import com.khoros.twitter.core.StatusMessage;
import com.khoros.twitter.core.Timeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

// Provides services for getting home timeline and posting tweets
public class TwitterLabService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterLabService.class);

    static final int CHAR_LIMIT = 280;
    static final String EXCEPTION_STR = "Error posting tweet. Try again later!";
    static final String CHAR_LIMIT_STR = "Invalid tweet: Tweet must be between 1-" + CHAR_LIMIT + " characters";
    static final String JSON_FORMAT_STR = "Invalid JSON - use format: {\"text\":\"<your tweet here>\"}";
    static final String TIMELINE_EXCEPTION_STR = "Error getting home timeline. Try again later!";

    private static volatile TwitterLabService instance = null;

    private Twitter twitter;

    private TwitterLabService(Twitter twitter) {
        this.twitter = twitter;
    }

    public static TwitterLabService getInstance(Twitter twitter) {
        LOGGER.info("Getting TwitterLabService instance.");
        if (instance == null) {
            synchronized(TwitterLabService.class) {
                if (instance == null) {
                    instance = new TwitterLabService(twitter);
                }
            }
        }
        return instance;
    }

    public Response postTweet(Message message) {
        LOGGER.info("Attempting to post tweet.");
        String text = message.getText();
        StatusMessage statusMessage = new StatusMessage();
        if (text == null) {
            LOGGER.warn("Failed to post tweet. " + JSON_FORMAT_STR);
            statusMessage.setStatus(JSON_FORMAT_STR);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(statusMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        try {
            if (text.length() > 0 && text.length() <= CHAR_LIMIT) {
                Status status = twitter.updateStatus(text);
                LOGGER.info("Successfully posted tweet: " + text);
                return Response.status(Response.Status.OK)
                        .entity(status)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            } else {
                LOGGER.warn("Failed to post tweet. " + CHAR_LIMIT_STR);
                statusMessage.setStatus(CHAR_LIMIT_STR);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(statusMessage)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        } catch (TwitterException e) {
            LOGGER.error("Failed to post tweet: " + text, e);
            statusMessage.setStatus(EXCEPTION_STR);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(statusMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    public Response getTimeline() {
        LOGGER.info("Attempting to retrieve home timeline.");
        try {
            List<Status> timeline = twitter.getHomeTimeline();
            LOGGER.info("Successfully retrieved home timeline.");
            return Response.status(Response.Status.OK)
                    .entity(new Timeline(timeline))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (TwitterException e) {
            LOGGER.error("Failed to get timeline. ", e);
            StatusMessage errorMessage = new StatusMessage(TIMELINE_EXCEPTION_STR);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

    }

}
