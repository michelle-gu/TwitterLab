package com.khoros.twitter;

import com.khoros.twitter.resources.TwitterLabResource;
import com.khoros.twitter.services.TwitterLabService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterLabApplication extends Application<TwitterLabConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterLabApplication.class);

    public static void main(String[] args) throws Exception {
        new TwitterLabApplication().run(args);
    }

    @Override
    public String getName() {
        return "twitter-lab";
    }

    @Override
    public void initialize(Bootstrap<TwitterLabConfiguration> bootstrap) {
        // nothing to do here yet
    }

    @Override
    public void run(TwitterLabConfiguration configuration,
                    Environment environment) {
        LOGGER.info("Running TwitterLab application.");
        TwitterLabService twitterLabService = TwitterLabService.getInstance(configuration);

        LOGGER.debug("Registering twitter lab resource with twitter instance.");
        final TwitterLabResource twitterLabResource = new TwitterLabResource(twitterLabService);
        environment.jersey().register(twitterLabResource);
    }

}
