package com.khoros.twitter.services;

import com.khoros.twitter.models.Post;
import com.khoros.twitter.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;

// Provides services for getting home timeline and posting tweets
public class TwitterLabService {

    public static final int CHAR_LIMIT = 280;
    public static final String CHAR_LIMIT_STR = "Invalid tweet: Tweet must be between 1-" + TwitterLabService.CHAR_LIMIT + " characters";
    public static final String JSON_FORMAT_STR = "Invalid JSON - use format: {\"message\":\"<your tweet here>\"}";
    public static final String EXCEPTION_STR = "Error posting tweet. Try again later!";
    public static final String TIMELINE_EXCEPTION_STR = "Error getting home timeline. Try again later!";

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterLabService.class);

    private Twitter twitter;

    public TwitterLabService(Twitter twitter) {
        this.twitter = twitter;
    }

    public Status postTweet(String message) throws TwitterLabException {
        LOGGER.info("Attempting to post tweet.");
        try {
            if (message != null && message.length() > 0 && message.length() <= CHAR_LIMIT) {
                Status status = twitter.updateStatus(message);
                LOGGER.info("Successfully posted tweet: " + message);
                return status;
            } else if (message == null) {
                LOGGER.warn("Failed to post tweet. " + JSON_FORMAT_STR);
                throw new TwitterLabException(JSON_FORMAT_STR);
            } else {
                LOGGER.warn("Failed to post tweet. " + CHAR_LIMIT_STR);
                throw new TwitterLabException(CHAR_LIMIT_STR);
            }
        } catch (TwitterException e) {
            LOGGER.error("Failed to post tweet: " + message, e);
            throw new TwitterLabException(EXCEPTION_STR);
        }
    }

    public List<Post> getTimeline() throws TwitterLabException {
        LOGGER.info("Attempting to retrieve home timeline.");
        try {
            List<Status> statusTimeline = twitter.getHomeTimeline();
            List<Post> postTimeline = Optional.ofNullable(statusTimeline)
                                              .map(List::stream)
                                              .orElseGet(Stream::empty)
                                              .map(s -> new Post(s.getText(),
                                                                 new User(s.getUser().getScreenName(),
                                                                          s.getUser().getName(),
                                                                          s.getUser().getProfileImageURL()),
                                                                 s.getCreatedAt()))
                                              .collect(toList());
            LOGGER.info("Successfully retrieved home timeline.");
            return postTimeline;
        } catch (TwitterException e) {
            LOGGER.error("Failed to get timeline. ", e);
            throw new TwitterLabException(TIMELINE_EXCEPTION_STR);
        }
    }

    public List<Post> getFilteredTimeline(String keyword) throws TwitterLabException{
        LOGGER.info("Attempting to retrieve home timeline.");
        try {
            List<Status> statusTimeline = twitter.getHomeTimeline();
            LOGGER.info("Filtering home timeline with keyword: " + keyword);
            List<Post> filteredPostTimeline = Optional.ofNullable(statusTimeline)
                                                      .map(List::stream)
                                                      .orElseGet(Stream::empty)
                                                      .filter(s -> s.getText().contains(keyword))
                                                      .map(s -> new Post(s.getText(),
                                                                         new User(s.getUser().getScreenName(),
                                                                                  s.getUser().getName(),
                                                                                  s.getUser().getProfileImageURL()),
                                                                         s.getCreatedAt()))
                                                      .collect(toList());
            LOGGER.info("Successfully retrieved home timeline.");
            return filteredPostTimeline;
        } catch (TwitterException e) {
            LOGGER.error("Failed to get timeline. ", e);
            throw new TwitterLabException(TIMELINE_EXCEPTION_STR);
        }
    }

}
