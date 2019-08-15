package com.khoros.twitter.resources;

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

public class TestTwitterLabResource {

    private static final String EXCEPTION_STR = "Error getting home timeline. Try again later!";

    private TwitterLabResource twitterLabResource;
    private Twitter mockedTwitter;

    @Before
    public void setUp() {
        mockedTwitter = mock(Twitter.class);
        twitterLabResource = new TwitterLabResource(mockedTwitter);
    }

    // GetTimeline tests
    @Test
    public void testGetTimeline() {
        Response testResponse = twitterLabResource.getTimeline();
        assertEquals(Response.Status.OK.getStatusCode(), testResponse.getStatus());
    }

    @Test
    public void testGetTimelineException() {
        when(twitterLabResource.getTimeline()).thenThrow(new TwitterException("Test exception"));
        Response testResponse = twitterLabResource.getTimeline();
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(EXCEPTION_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    // PostTweet tests
    @Test
    public void testPostEmptyTweet() {
        Message message = new Message("");
        Response testResponse = twitterLabResource.postTweet(message);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabResource.CHAR_LIMIT_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    @Test
    public void testPostValidTweet() {
        Message message = new Message("Test tweet");
        assertEquals(Response.Status.OK.getStatusCode(), twitterLabResource.postTweet(message).getStatus());
    }

    @Test
    public void testPostLongTweet() {
        Message message = new Message(StringUtils.repeat("*", TwitterLabResource.CHAR_LIMIT + 1));
        Response testResponse = twitterLabResource.postTweet(message);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabResource.CHAR_LIMIT_STR, ((StatusMessage)testResponse.getEntity()).getStatus());

    }

    @Test
    public void testPostTweetException() {
        Message message = new Message("Test tweet");
        when(twitterLabResource.postTweet(message)).thenThrow(new TwitterException("Test exception"));
        Response testResponse = twitterLabResource.postTweet(message);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabResource.EXCEPTION_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    @Test
    public void testPostNullTweet() {
        // Ex: Invalid JSON passed in or null text
        Message message = new Message(null);
        Response testResponse = twitterLabResource.postTweet(message);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabResource.JSON_FORMAT_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

}
