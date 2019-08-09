package com.khoros.twitter.resources;

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
import java.util.ArrayList;
import java.util.List;

// This route will retrieve a list of latest tweets from the home timeline
@Path("/api/1.0/twitter/timeline")
@Produces(MediaType.APPLICATION_JSON)
public class GetTimelineResource {

    public GetTimelineResource() {
    }

    @GET
    @Timed
    public Timeline getTimeline() {
        List<Status> timeline = new ArrayList<Status>();
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            timeline = twitter.getHomeTimeline();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return new Timeline(timeline);
    }
}
