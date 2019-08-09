package com.khoros.twitter.resources;

import com.khoros.twitter.core.StatusMessage;
import com.khoros.twitter.core.Timeline;
import com.codahale.metrics.annotation.Timed;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
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

    @GET
    @Timed
    public Response getTimeline() {
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            List<Status> timeline = twitter.getHomeTimeline();
            return Response.status(Response.Status.OK)
                    .entity(new Timeline(timeline))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (TwitterException e) {
            StatusMessage errorMessage = new StatusMessage("Twitter exception: " + e.getErrorMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
