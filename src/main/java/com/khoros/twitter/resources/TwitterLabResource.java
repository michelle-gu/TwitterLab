package com.khoros.twitter.resources;

import com.codahale.metrics.annotation.Timed;
import com.khoros.twitter.core.StatusMessage;
import com.khoros.twitter.core.Timeline;
import com.khoros.twitter.models.Post;
import com.khoros.twitter.services.TwitterLabException;
import com.khoros.twitter.services.TwitterLabService;
import org.slf4j.LoggerFactory;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Provides endpoints for retrieving home timeline and posting tweets
@Path("/api/1.0/twitter")
@Produces(MediaType.APPLICATION_JSON)
public class TwitterLabResource {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TwitterLabResource.class);

    static final String NULL_KEYWORD_STR = "Must define keyword parameter to filter on.";

    private TwitterLabService twitterLabService;

    @Inject
    public TwitterLabResource(TwitterLabService twitterLabService) {
        this.twitterLabService = twitterLabService;
    }

    @Path("tweet")
    @POST
    @Timed
    public Response postTweet(Post post) {
        LOGGER.trace("Hitting postTweet endpoint");
        try {
            return Response.status(Response.Status.OK)
                    .entity(twitterLabService.postTweet(post.getMessage()))
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

    @Path("timeline/filter")
    @GET
    @Timed
    public Response getFilteredTimeline(@QueryParam("keyword") String keyword) {
        LOGGER.trace("Hitting getFilteredTimeline endpoint");
        if (keyword == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new StatusMessage(NULL_KEYWORD_STR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        try {
            return twitterLabService.getFilteredTimeline(keyword)
                    .map(filteredTimeline -> Response.status(Response.Status.OK)
                    .entity(new Timeline(filteredTimeline))
                    .type(MediaType.APPLICATION_JSON)
                    .build())
                    .orElse(Response.status(Response.Status.OK)
                            .entity(new Timeline())
                            .type(MediaType.APPLICATION_JSON)
                            .build());
        } catch (TwitterLabException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new StatusMessage(e.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

}
