package com.khoros.twitter.resources;

import com.codahale.metrics.annotation.Timed;
import com.khoros.twitter.core.Message;
import com.khoros.twitter.core.StatusMessage;
import twitter4j.*;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// This route will post the message to the Twitter account.
@Path("/api/1.0/twitter/tweet")
@Produces(MediaType.APPLICATION_JSON)
public class PostTweetResource {

    static final int CHAR_LIMIT = 280;
    static final String EXCEPTION_STR = "Error posting tweet. Try again later!";
    static final String CHAR_LIMIT_STR = "Invalid tweet: Tweet must be between 1-" + CHAR_LIMIT + " characters";
    static final String JSON_FORMAT_STR = "Invalid JSON - use format: {\"text\":\"<your tweet here>\"}";

    private Twitter twitter;

    public PostTweetResource(Twitter twitter) {
        this.twitter = twitter;
    }

    public PostTweetResource() {
        twitter = TwitterFactory.getSingleton();
    }

    @POST
    @Timed
    public Response postTweet(Message message) {
        String text = message.getText();
        StatusMessage statusMessage = new StatusMessage();
        if (text == null) {
            statusMessage.setStatus(JSON_FORMAT_STR);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(statusMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        try {
            if (text.length() > 0 && text.length() <= CHAR_LIMIT) {
                Status status = twitter.updateStatus(text);
                return Response.status(Response.Status.OK)
                        .entity(status)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            } else {
                statusMessage.setStatus(CHAR_LIMIT_STR);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(statusMessage)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        } catch (TwitterException e) {
            statusMessage.setStatus(EXCEPTION_STR);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(statusMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}

