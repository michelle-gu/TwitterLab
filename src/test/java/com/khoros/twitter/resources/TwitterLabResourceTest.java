package com.khoros.twitter.resources;

import com.khoros.twitter.core.Message;
import com.khoros.twitter.core.StatusMessage;
import com.khoros.twitter.services.TwitterLabException;
import com.khoros.twitter.services.TwitterLabService;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import javax.ws.rs.core.Response;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

public class TwitterLabResourceTest {

    private TwitterLabResource twitterLabResource;
    private TwitterLabService mockedTwitterLabService;

    @Before
    public void setUp() {
        mockedTwitterLabService = mock(TwitterLabService.class);
        twitterLabResource = new TwitterLabResource(mockedTwitterLabService);
    }

    // GetTimeline tests
    @Test
    public void testGetTimeline() {
        Response testResponse = twitterLabResource.getTimeline();
        assertEquals(Response.Status.OK.getStatusCode(), testResponse.getStatus());
    }

    @Test
    public void testGetTimelineException() throws TwitterLabException {
        when(mockedTwitterLabService.getTimeline()).thenThrow(new TwitterLabException(TwitterLabService.TIMELINE_EXCEPTION_STR));
        Response testResponse = twitterLabResource.getTimeline();
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabService.TIMELINE_EXCEPTION_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    // PostTweet tests
    @Test
    public void testPostEmptyTweet() throws TwitterLabException {
        Message message = new Message("");
        when(mockedTwitterLabService.postTweet(message.getText())).thenThrow(new TwitterLabException(TwitterLabService.CHAR_LIMIT_STR));
        Response testResponse = twitterLabResource.postTweet(message);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabService.CHAR_LIMIT_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    @Test
    public void testPostValidTweet() {
        Message message = new Message("Test text");
        Response testResponse = twitterLabResource.postTweet(message);
        assertEquals(Response.Status.OK.getStatusCode(), testResponse.getStatus());
    }

    @Test
    public void testPostLongTweet() throws TwitterLabException {
        Message message = new Message(StringUtils.repeat("*", TwitterLabService.CHAR_LIMIT + 1));
        when(mockedTwitterLabService.postTweet(message.getText())).thenThrow(new TwitterLabException(TwitterLabService.CHAR_LIMIT_STR));
        Response testResponse = twitterLabResource.postTweet(message);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabService.CHAR_LIMIT_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    @Test
    public void testPostTweetException() throws TwitterLabException {
        Message message = new Message("Test tweet");
        when(mockedTwitterLabService.postTweet(message.getText())).thenThrow(new TwitterLabException(TwitterLabService.EXCEPTION_STR));
        Response testResponse = twitterLabResource.postTweet(message);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabService.EXCEPTION_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    @Test
    public void testPostNullTweet() throws TwitterLabException {
        // Ex: Invalid JSON passed in or null text
        Message message = new Message(null);
        when(mockedTwitterLabService.postTweet(message.getText())).thenThrow(new TwitterLabException(TwitterLabService.JSON_FORMAT_STR));
        Response testResponse = twitterLabResource.postTweet(message);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabService.JSON_FORMAT_STR, ((StatusMessage) testResponse.getEntity()).getStatus());
    }

}
