package com.khoros.twitter;

import com.khoros.twitter.resources.GetTimelineResource;
import com.khoros.twitter.resources.PostTweetResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

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
        LOGGER.info("Running TwitterLab application. Configuring twitter factory.");
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(configuration.getTwitterLabFactory().getConsumerKey())
                .setOAuthConsumerSecret(configuration.getTwitterLabFactory().getConsumerSecret())
                .setOAuthAccessToken(configuration.getTwitterLabFactory().getAccessToken())
                .setOAuthAccessTokenSecret(configuration.getTwitterLabFactory().getAccessTokenSecret());
        LOGGER.debug("Creating twitter instance with configuration.");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        LOGGER.debug("Registering post tweet resource with twitter instance.");
        final PostTweetResource postTweetResource = new PostTweetResource(twitter);
        environment.jersey().register(postTweetResource);
        LOGGER.debug("Registering get timeline resource with twitter instance.");
        final GetTimelineResource getTimelineResource = new GetTimelineResource(twitter);
        environment.jersey().register(getTimelineResource);
    }

}
