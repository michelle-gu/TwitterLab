package com.khoros.twitter.resources;

import com.codahale.metrics.annotation.Timed;
import com.khoros.twitter.core.Message;
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
        return twitterLabService.postTweet(message);
    }

    @Path("timeline")
    @GET
    @Timed
    public Response getTimeline() {
        LOGGER.trace("Hitting getTimeline endpoint");
        return twitterLabService.getTimeline();
    }

}
