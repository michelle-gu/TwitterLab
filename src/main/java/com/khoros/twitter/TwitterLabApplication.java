package com.khoros.twitter;

import com.khoros.twitter.resources.GetTimelineResource;
import com.khoros.twitter.resources.PostTweetResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TwitterLabApplication extends Application<TwitterLabConfiguration> {

    public static void main(String[] args) throws Exception {
        new TwitterLabApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<TwitterLabConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(TwitterLabConfiguration configuration,
                    Environment environment) {
        final PostTweetResource postTweetResource = new PostTweetResource();
        environment.jersey().register(postTweetResource);
        final GetTimelineResource getTimelineResource = new GetTimelineResource();
        environment.jersey().register(getTimelineResource);
    }

}
