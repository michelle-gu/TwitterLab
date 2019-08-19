package com.khoros.twitter.services;

import com.khoros.twitter.models.Post;
import com.khoros.twitter.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.List;

// Provides services for getting home timeline and posting tweets
public class TwitterLabService {

    public static final int CHAR_LIMIT = 280;
    public static final String CHAR_LIMIT_STR = "Invalid tweet: Tweet must be between 1-" + TwitterLabService.CHAR_LIMIT + " characters";
    public static final String JSON_FORMAT_STR = "Invalid JSON - use format: {\"text\":\"<your tweet here>\"}";
    public static final String EXCEPTION_STR = "Error posting tweet. Try again later!";
    public static final String TIMELINE_EXCEPTION_STR = "Error getting home timeline. Try again later!";

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterLabService.class);

    private Twitter twitter;

    public TwitterLabService(Twitter twitter) {
        this.twitter = twitter;
    }

    public Status postTweet(String text) throws TwitterLabException {
        LOGGER.info("Attempting to post tweet.");
        try {
            if (text != null && text.length() > 0 && text.length() <= CHAR_LIMIT) {
                Status status = twitter.updateStatus(text);
                LOGGER.info("Successfully posted tweet: " + text);
                return status;
            } else if (text == null) {
                LOGGER.warn("Failed to post tweet. " + JSON_FORMAT_STR);
                throw new TwitterLabException(JSON_FORMAT_STR);
            } else {
                LOGGER.warn("Failed to post tweet. " + CHAR_LIMIT_STR);
                throw new TwitterLabException(CHAR_LIMIT_STR);
            }
        } catch (TwitterException e) {
            LOGGER.error("Failed to post tweet: " + text, e);
            throw new TwitterLabException(EXCEPTION_STR);
        }
    }

    public List<Post> getTimeline() throws TwitterLabException {
        LOGGER.info("Attempting to retrieve home timeline.");
        try {
            List<Status> statusTimeline = twitter.getHomeTimeline();
            List<Post> postTimeline = new ArrayList<Post>();
            if (statusTimeline != null) {
                for (Status s : statusTimeline) {
                    User user = new User(s.getUser().getScreenName(), s.getUser().getName(), s.getUser().getProfileImageURL());
                    postTimeline.add(new Post(s.getText(), user, s.getCreatedAt()));
                }
            }
            LOGGER.info("Successfully retrieved home timeline.");
            return postTimeline;
        } catch (TwitterException e) {
            LOGGER.error("Failed to get timeline. ", e);
            throw new TwitterLabException(TIMELINE_EXCEPTION_STR);
        }
    }

}
