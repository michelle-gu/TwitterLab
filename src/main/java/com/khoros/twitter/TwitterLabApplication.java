package com.khoros.twitter;

import com.khoros.twitter.resources.GetTimelineResource;
import com.khoros.twitter.resources.PostTweetResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterLabApplication extends Application<TwitterLabConfiguration> {

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
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(configuration.getConsumerKey())
                .setOAuthConsumerSecret(configuration.getConsumerSecret())
                .setOAuthAccessToken(configuration.getAccessToken())
                .setOAuthAccessTokenSecret(configuration.getAccessTokenSecret());
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        final PostTweetResource postTweetResource = new PostTweetResource(twitter);
        environment.jersey().register(postTweetResource);
        final GetTimelineResource getTimelineResource = new GetTimelineResource(twitter);
        environment.jersey().register(getTimelineResource);
    }

}
