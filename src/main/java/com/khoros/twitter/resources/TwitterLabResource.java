package com.khoros.twitter.resources;

import com.codahale.metrics.annotation.Timed;
import com.khoros.twitter.core.Message;
import com.khoros.twitter.core.StatusMessage;
import com.khoros.twitter.core.Timeline;
import com.khoros.twitter.services.TwitterLabException;
import com.khoros.twitter.services.TwitterLabService;
import org.slf4j.LoggerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Provides endpoints for retrieving home timeline and posting tweets
@Path("/api/1.0/twitter")
@Produces(MediaType.APPLICATION_JSON)
public class TwitterLabResource {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TwitterLabResource.class);

    private TwitterLabService twitterLabService;

    public TwitterLabResource(TwitterLabService twitterLabService) {
        this.twitterLabService = twitterLabService;
    }

    @Path("tweet")
    @POST
    @Timed
    public Response postTweet(Message message) {
        LOGGER.trace("Hitting postTweet endpoint");
        try {
            return Response.status(Response.Status.OK)
                    .entity(twitterLabService.postTweet(message.getText()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (TwitterLabException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new StatusMessage(e.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @Path("timeline")
    @GET
    @Timed
    public Response getTimeline() {
        LOGGER.trace("Hitting getTimeline endpoint");
        try {
            return Response.status(Response.Status.OK)
                    .entity(new Timeline(twitterLabService.getTimeline()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (TwitterLabException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new StatusMessage(e.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
