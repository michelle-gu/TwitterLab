package com.khoros.twitter.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import static org.mockito.Mockito.*;

public class TwitterLabServiceTest {

    private TwitterLabService twitterLabService;
    private Twitter mockedTwitter;

    @Before
    public void setUp() {
        mockedTwitter = mock(Twitter.class);
        twitterLabService = new TwitterLabService(mockedTwitter);
    }

    @After
    public void reset() {
        Mockito.reset(mockedTwitter);
    }

    @Test
    public void testGetTimeline() throws TwitterException {
        String text = "test";
        Status testStatus = twitterLabService.postTweet(text);
        verify(mockedTwitter).updateStatus(text);
    }

    @Test
    public void testPostTweet() throws TwitterException {
        String text = "test";
        Status testStatus = twitterLabService.postTweet(text);
        verify(mockedTwitter).updateStatus(text);
    }

}
