package com.khoros.twitter;

import com.khoros.twitter.services.TwitterLabService;
import dagger.Module;
import dagger.Provides;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Module
public class TwitterLabModule {

    private TwitterLabConfiguration configuration;

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterLabApplication.class);

    public  TwitterLabModule(TwitterLabConfiguration configuration) {
        this.configuration = configuration;
    }

    @Provides
    public Twitter provideTwitter() {
        LOGGER.info("Configuring TwitterFactory.");
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(configuration.getTwitterLabFactory().getConsumerKey())
                .setOAuthConsumerSecret(configuration.getTwitterLabFactory().getConsumerSecret())
                .setOAuthAccessToken(configuration.getTwitterLabFactory().getAccessToken())
                .setOAuthAccessTokenSecret(configuration.getTwitterLabFactory().getAccessTokenSecret());

        LOGGER.debug("Creating Twitter Factory.");
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }

    @Provides
    public TwitterLabService provideTwitterLabService(Twitter t) {
        return new TwitterLabService(t);
    }

}
