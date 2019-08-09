package com.khoros.twitter.resources;

import com.codahale.metrics.annotation.Timed;
import com.khoros.twitter.core.Message;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
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
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            if (text.length() > 0 || text.length() <= 280) {
                status = twitter.updateStatus(text);
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .header("Invalid tweet","Tweet must be between 1-280 characters.")
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .build();
            }
        } catch (TwitterException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .header("Twitter exception", e.getErrorMessage())
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }

        return Response.status(Response.Status.OK)
                .header("Successfully tweeted", status.getText())
                .build();
    }
}

