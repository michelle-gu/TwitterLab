package com.khoros.twitter.services;

import com.khoros.twitter.core.Message;
import com.khoros.twitter.core.StatusMessage;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import javax.ws.rs.core.Response;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestTwitterLabService {

    private TwitterLabService twitterLabService1;
    private TwitterLabService twitterLabService2;
    private Twitter mockedTwitter;

    @Before
    public void setUp() {
        mockedTwitter = mock(Twitter.class);
        twitterLabService1 = TwitterLabService.getInstance(mockedTwitter);
        twitterLabService2 = TwitterLabService.getInstance(mockedTwitter);
    }

    @Test
    public void testIsSingleton() {
        assertEquals(twitterLabService1, twitterLabService2);
    }

    // GetTimeline tests
    @Test
    public void testGetTimeline() {
        Response testResponse = twitterLabService1.getTimeline();
        assertEquals(Response.Status.OK.getStatusCode(), testResponse.getStatus());
    }

    @Test
    public void testGetTimelineException() {
        when(twitterLabService1.getTimeline()).thenThrow(new TwitterException("Test exception"));
        Response testResponse = twitterLabService1.getTimeline();
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabService.TIMELINE_EXCEPTION_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    // PostTweet tests
    @Test
    public void testPostEmptyTweet() {
        Message message = new Message("");
        Response testResponse = twitterLabService1.postTweet(message);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabService.CHAR_LIMIT_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    @Test
    public void testPostValidTweet() {
        Message message = new Message("Test tweet");
        assertEquals(Response.Status.OK.getStatusCode(), twitterLabService1.postTweet(message).getStatus());
    }

    @Test
    public void testPostLongTweet() {
        Message message = new Message(StringUtils.repeat("*", TwitterLabService.CHAR_LIMIT + 1));
        Response testResponse = twitterLabService1.postTweet(message);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabService.CHAR_LIMIT_STR, ((StatusMessage)testResponse.getEntity()).getStatus());

    }

    @Test
    public void testPostTweetException() {
        Message message = new Message("Test tweet");
        when(twitterLabService1.postTweet(message)).thenThrow(new TwitterException("Test exception"));
        Response testResponse = twitterLabService1.postTweet(message);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabService.EXCEPTION_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    @Test
    public void testPostNullTweet() {
        // Ex: Invalid JSON passed in or null text
        Message message = new Message(null);
        Response testResponse = twitterLabService1.postTweet(message);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabService.JSON_FORMAT_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

}
