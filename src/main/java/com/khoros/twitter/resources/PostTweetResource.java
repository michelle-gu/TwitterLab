package com.khoros.twitter.resources;

import com.codahale.metrics.annotation.Timed;
import com.khoros.twitter.core.Message;
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

    public PostTweetResource() {
    }

    @POST
    @Timed
    public Response postTweet(Message message) {
        // Post message
        String text = message.getText();
        Status status = null;
        Message statusMessage = new Message();
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            if (text.length() > 0 || text.length() <= 280) {
                status = twitter.updateStatus(text);
            } else {
                statusMessage.setText("Invalid tweet: Tweet must be between 1-280 characters.");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(statusMessage)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        } catch (TwitterException e) {
            statusMessage.setText("Twitter exception: " + e.getErrorMessage() + " Tweet must be between 1-280 characters.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(statusMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        statusMessage.setText("Successfully tweeted: " + status.getText());
        return Response.status(Response.Status.OK)
                .entity(statusMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

