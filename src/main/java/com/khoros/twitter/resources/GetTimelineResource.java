package com.khoros.twitter.resources;

import com.khoros.twitter.core.StatusMessage;
import com.khoros.twitter.core.Timeline;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

// This route will retrieve a list of latest tweets from the home timeline
@Path("/api/1.0/twitter/timeline")
@Produces(MediaType.APPLICATION_JSON)
public class GetTimelineResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetTimelineResource.class);

    private Twitter twitter;

    public GetTimelineResource(Twitter twitter) {
        this.twitter = twitter;
    }

    @GET
    @Timed
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
            StatusMessage errorMessage = new StatusMessage("Error getting home timeline. Try again later!");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
