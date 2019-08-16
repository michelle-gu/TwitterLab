package com.khoros.twitter.services;

import com.khoros.twitter.TwitterLabConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterLabService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterLabService.class);

    private static volatile TwitterLabService instance = null;

    private static Twitter twitter;

    private TwitterLabService(TwitterLabConfiguration configuration) {
        LOGGER.info("Configuring TwitterFactory.");
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(configuration.getTwitterLabFactory().getConsumerKey())
                .setOAuthConsumerSecret(configuration.getTwitterLabFactory().getConsumerSecret())
                .setOAuthAccessToken(configuration.getTwitterLabFactory().getAccessToken())
                .setOAuthAccessTokenSecret(configuration.getTwitterLabFactory().getAccessTokenSecret());

        LOGGER.debug("Creating Twitter instance.");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    public static TwitterLabService getInstance(TwitterLabConfiguration configuration) {
        LOGGER.info("Getting TwitterLabService instance.");
        if (instance == null) {
            synchronized(TwitterLabService.class) {
                if (instance == null) {
                    instance = new TwitterLabService(configuration);
                }
            }
        }
        return instance;
    }

    public Twitter getTwitter() {
        return twitter;
    }

}
