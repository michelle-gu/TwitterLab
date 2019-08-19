package com.khoros.twitter.resources;

import com.codahale.metrics.annotation.Timed;
import com.khoros.twitter.core.Message;
import com.khoros.twitter.core.StatusMessage;
import com.khoros.twitter.core.Timeline;
import com.khoros.twitter.services.TwitterLabService;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.TwitterException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

// Provides endpoints for retrieving home timeline and posting tweets
@Path("/api/1.0/twitter")
@Produces(MediaType.APPLICATION_JSON)
public class TwitterLabResource {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TwitterLabResource.class);

    static final int CHAR_LIMIT = 280;
    static final String EXCEPTION_STR = "Error posting tweet. Try again later!";
    static final String CHAR_LIMIT_STR = "Invalid tweet: Tweet must be between 1-" + CHAR_LIMIT + " characters";
    static final String JSON_FORMAT_STR = "Invalid JSON - use format: {\"text\":\"<your tweet here>\"}";
    static final String TIMELINE_EXCEPTION_STR = "Error getting home timeline. Try again later!";

    private TwitterLabService twitterLabService;

    public TwitterLabResource(TwitterLabService twitterLabService) {
        this.twitterLabService = twitterLabService;
    }

    @Path("tweet")
    @POST
    @Timed
    public Response postTweet(Message message) {
        LOGGER.trace("Hitting postTweet endpoint");
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
                Status status = twitterLabService.postTweet(text);
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

    @Path("timeline")
    @GET
    @Timed
    public Response getTimeline() {
        LOGGER.trace("Hitting getTimeline endpoint");
        LOGGER.info("Attempting to retrieve home timeline.");
        try {
            List<Status> timeline = twitterLabService.getTimeline();
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
