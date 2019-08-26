package com.khoros.twitter.services;

import com.khoros.twitter.TwitterLabCache;
import com.khoros.twitter.models.Post;
import com.khoros.twitter.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;

// Provides services for getting home timeline and posting tweets
public class TwitterLabService {

    public static final int CHAR_LIMIT = 280;
    public static final String CHAR_LIMIT_STR = "Invalid tweet: Tweet must be between 1-" + TwitterLabService.CHAR_LIMIT + " characters";
    public static final String JSON_FORMAT_STR = "Invalid JSON - use format: {\"message\":\"<your tweet here>\"}";
    public static final String EXCEPTION_STR = "Error posting tweet. Try again later!";
    public static final String TIMELINE_EXCEPTION_STR = "Error getting home timeline. Try again later!";

    private TwitterLabCache cache;

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterLabService.class);

    private Twitter twitter;

    @Inject
    public TwitterLabService(Twitter twitter) {
        cache = new TwitterLabCache();
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
        List<Post> postTimeline = getCachedTimeline();
        LOGGER.info("Successfully retrieved home timeline.");
        return postTimeline;
    }

    public Optional<List<Post>> getFilteredTimeline(String keyword) throws TwitterLabException {
        LOGGER.info("Attempting to retrieve home timeline.");
        List<Post> postTimeline = getCachedTimeline();
        LOGGER.info("Filtering home timeline with keyword: " + keyword);
        List<Post> filteredPostTimeline = Optional.ofNullable(postTimeline)
                                                  .map(List::stream)
                                                  .orElseGet(Stream::empty)
                                                  .filter(post -> post.getMessage().toLowerCase().contains(keyword))
                                                  .collect(toList());
        LOGGER.info("Successfully retrieved home timeline.");
        return Optional.of(filteredPostTimeline);
    }

    // Gets cached timeline and updates cache as needed
    private List<Post> getCachedTimeline() throws TwitterLabException {
        List<Post> postTimeline;
        try {
            List<Status> statusTimeline = twitter.getHomeTimeline();
            cache.clear();
            Optional.ofNullable(statusTimeline)
                    .map(List::stream)
                    .orElseGet(Stream::empty)
                    .map(status -> cache.put(status.getId(),
                                             new Post(status.getText(),
                                                      new User(status.getUser().getScreenName(),
                                                               status.getUser().getName(),
                                                               status.getUser().getProfileImageURL()),
                                                      status.getCreatedAt())))
                    .collect(toList());
            postTimeline = new ArrayList<Post>(cache.values());
            cache.setLastUpdated(new Date());
            LOGGER.info("Updated cache.");
        } catch (TwitterException e) {
            LOGGER.info("Failed to get timeline from Twitter. Attempting to retrieve from cache.");
            postTimeline = new ArrayList<Post>(cache.values());
            if (postTimeline.isEmpty()) {
                LOGGER.error("Failed to retrieve from both Twitter and cache.", e);
                throw new TwitterLabException(TIMELINE_EXCEPTION_STR);
            } else {
                LOGGER.info("Retrieved from cache.");
            }
        }
        return postTimeline;
    }

}
