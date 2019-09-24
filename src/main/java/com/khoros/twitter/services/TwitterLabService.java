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
    public static final String TIMELINE_EXCEPTION_STR = "Error getting timeline. Try again later!";
    public static final String NULL_KEYWORD_STR = "Failed to get filtered timeline. Filter keyword cannot be null or empty.";

    private TwitterLabCache<String, ArrayList<Post>> cache;

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterLabService.class);

    private Twitter twitter;

    @Inject
    public TwitterLabService(Twitter twitter) {
        cache = new TwitterLabCache<String, ArrayList<Post>>();
        this.twitter = twitter;
    }

    public Status postTweet(String message) throws TwitterLabException {
        LOGGER.info("Attempting to post tweet.");
        try {
            if (message != null && message.length() > 0 && message.length() <= CHAR_LIMIT) {
                Status status = twitter.updateStatus(message);
                LOGGER.info("Successfully posted tweet: " + message);
                cache.clear();
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
        LOGGER.info("Attempting to retrieve home timeline from cache.");
        if (!cache.containsKey("timeline") || cache.get("timeline").size() == 0) {
            try {
                List<Status> statusTimeline = twitter.getHomeTimeline();
                cache.put("timeline", createPostTimeline(statusTimeline));
                LOGGER.info("Updated cache.");
            } catch (TwitterException e) {
                LOGGER.error("Failed to retrieve timeline.", e);
                throw new TwitterLabException(TIMELINE_EXCEPTION_STR);
            }
        }
        List<Post> postTimeline = cache.get("timeline");
        LOGGER.info("Successfully retrieved home timeline from cache.");
        return postTimeline;
    }

    public List<Post> getUserTimeline() throws TwitterLabException {
        LOGGER.info("Attempting to retrieve user timeline from cache.");
        if (!cache.containsKey("userTimeline") || cache.get("userTimeline").size() == 0) {
            try {
                List<Status> statusTimeline = twitter.getUserTimeline();
                cache.put("userTimeline", createPostTimeline(statusTimeline));
                LOGGER.info("Updated cache.");
            } catch (TwitterException e) {
                LOGGER.error("Failed to retrieve timeline.", e);
                throw new TwitterLabException(TIMELINE_EXCEPTION_STR);
            }
        }
        List<Post> postTimeline = cache.get("userTimeline");
        LOGGER.info("Successfully retrieved user timeline from cache.");
        return postTimeline;
    }

    public Optional<List<Post>> getFilteredTimeline(String keyword) throws TwitterLabException {
        LOGGER.info("Attempting to retrieve home timeline from cache.");
        if (keyword == null || keyword.equals("")) {
            throw new TwitterLabException(NULL_KEYWORD_STR);
        }
        if (!cache.containsKey("timeline") || cache.get("timeline").size() == 0) {
            try {
                List<Status> statusTimeline = twitter.getHomeTimeline();
                cache.put("timeline", createPostTimeline(statusTimeline));
                LOGGER.info("Updated cache.");
            } catch (TwitterException e) {
                LOGGER.error("Failed to retrieve timeline.", e);
                throw new TwitterLabException(TIMELINE_EXCEPTION_STR);
            }
        }
        List<Post> postTimeline = cache.get("timeline");
        LOGGER.info("Filtering home timeline with keyword: " + keyword);
        List<Post> filteredPostTimeline = Optional.ofNullable(postTimeline)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .filter(post -> post.getMessage().toLowerCase().contains(keyword))
                .collect(toList());
        LOGGER.info("Successfully retrieved home timeline from cache.");
        return Optional.of(filteredPostTimeline);
    }

    private ArrayList<Post> createPostTimeline(List<Status> statusTimeline) {
            ArrayList<Post> postTimeline = new ArrayList<Post>();
            Optional.ofNullable(statusTimeline)
                    .map(List::stream) // List streaming maintains timeline order
                    .orElseGet(Stream::empty)
                    .map(status -> postTimeline.add(new Post(status.getText(),
                                                    new User(status.getUser().getScreenName(),
                                                            status.getUser().getName(),
                                                            status.getUser().getProfileImageURL()),
                                                    status.getCreatedAt(),
                                                    "https://twitter.com/" + status.getUser().getScreenName()
                                                        + "/status/" + status.getId())))
                    .collect(toList());
            return postTimeline;
    }

}
