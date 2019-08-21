package com.khoros.twitter.services;

import com.khoros.twitter.models.Post;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class TwitterLabServiceTest {

    private TwitterLabService twitterLabService;
    private Twitter mockedTwitter;

    @Before
    public void setUp() {
        mockedTwitter = mock(Twitter.class);
        twitterLabService = new TwitterLabService(mockedTwitter);
    }

    // Get timeline tests
    @Test
    public void testGetTimeline() throws TwitterLabException, TwitterException {
        List<Post> testTimeline = twitterLabService.getTimeline();
        verify(mockedTwitter).getHomeTimeline();
    }

    @Test (expected = TwitterLabException.class)
    public void testGetTimelineException() throws TwitterLabException, TwitterException {
        when(mockedTwitter.getHomeTimeline()).thenThrow(new TwitterException("Test Exception"));
        List<Post> testTimeline = twitterLabService.getTimeline();
    }

    // Get filtered timeline tests
    @Test
    public void testGetFilteredTimeline() throws TwitterLabException, TwitterException {
        Optional<List<Post>> testTimeline = twitterLabService.getFilteredTimeline("test");
        verify(mockedTwitter).getHomeTimeline();
    }

    @Test (expected = TwitterLabException.class)
    public void testGetFilteredTimelineException() throws TwitterLabException, TwitterException {
        when(mockedTwitter.getHomeTimeline()).thenThrow(new TwitterException("Test Exception"));
        Optional<List<Post>> testTimeline = twitterLabService.getFilteredTimeline("test");
    }

    @Test
    public void testGetFilteredTimelineWithEmptyFilter() throws TwitterLabException, TwitterException {
        Optional<List<Post>> testTimeline = twitterLabService.getFilteredTimeline("");
        verify(mockedTwitter).getHomeTimeline();
    }

    // Post tweet tests
    @Test
    public void testPostTweet() throws TwitterException, TwitterLabException {
        String text = "test";
        Status testStatus = twitterLabService.postTweet(text);
        verify(mockedTwitter).updateStatus(text);
    }

    @Test (expected = TwitterLabException.class)
    public void testPostEmptyTweet() throws TwitterLabException {
        String text = "";
        Status testStatus = twitterLabService.postTweet(text);
    }

    @Test (expected = TwitterLabException.class)
    public void testPostLongTweet() throws TwitterLabException {
        String text = StringUtils.repeat("*", TwitterLabService.CHAR_LIMIT + 1);
        Status testStatus = twitterLabService.postTweet(text);
    }

    @Test (expected = TwitterLabException.class)
    public void testPostNullTweet() throws TwitterLabException {
        String text = null;
        Status testStatus = twitterLabService.postTweet(text);
    }

    @Test (expected = TwitterLabException.class)
    public void testPostTweetException() throws TwitterLabException {
        String text = "test";
        when(twitterLabService.postTweet(text)).thenThrow(new TwitterException("Test exception"));
        Status testStatus = twitterLabService.postTweet(text);
    }

}
