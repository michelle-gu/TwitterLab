package com.khoros.twitter.resources;

import com.khoros.twitter.core.StatusMessage;
import com.khoros.twitter.models.Post;
import com.khoros.twitter.models.User;
import com.khoros.twitter.services.TwitterLabException;
import com.khoros.twitter.services.TwitterLabService;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import javax.ws.rs.core.Response;
import java.util.Date;
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

    // GetFilteredTimeline tests
    @Test
    public void testGetFilteredTimeline() {
        Response testResponse = twitterLabResource.getFilteredTimeline("test");
        assertEquals(Response.Status.OK.getStatusCode(), testResponse.getStatus());
    }

    @Test
    public void testGetFilteredTimelineWithEmptyFilter() {
        Response testResponse = twitterLabResource.getFilteredTimeline("");
        assertEquals(Response.Status.OK.getStatusCode(), testResponse.getStatus());
    }

    @Test
    public void testGetFilteredTimelineWithNullFilter() throws TwitterLabException {
        when(mockedTwitterLabService.getFilteredTimeline(null)).thenThrow(new TwitterLabException(TwitterLabService.NULL_KEYWORD_STR));
        Response testResponse = twitterLabResource.getFilteredTimeline(null);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabService.NULL_KEYWORD_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    @Test
    public void testGetFilteredTimelineException() throws TwitterLabException {
        when(mockedTwitterLabService.getFilteredTimeline("test")).thenThrow(new TwitterLabException(TwitterLabService.TIMELINE_EXCEPTION_STR));
        Response testResponse = twitterLabResource.getFilteredTimeline("test");
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabService.TIMELINE_EXCEPTION_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    // PostTweet tests
    @Test
    public void testPostEmptyTweet() throws TwitterLabException {
        User user = new User("test", "test", "");
        Post post = new Post("", user, new Date(), "");
        when(mockedTwitterLabService.postTweet(post.getMessage())).thenThrow(new TwitterLabException(TwitterLabService.CHAR_LIMIT_STR));
        Response testResponse = twitterLabResource.postTweet(post);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabService.CHAR_LIMIT_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    @Test
    public void testPostValidTweet() {
        User user = new User("test", "test", "");
        Post post = new Post("Test text", user, new Date(), "");
        Response testResponse = twitterLabResource.postTweet(post);
        assertEquals(Response.Status.OK.getStatusCode(), testResponse.getStatus());
    }

    @Test
    public void testPostLongTweet() throws TwitterLabException {
        User user = new User("test", "test", "");
        Post post = new Post(StringUtils.repeat("*", TwitterLabService.CHAR_LIMIT + 1), user, new Date(), "");
        when(mockedTwitterLabService.postTweet(post.getMessage())).thenThrow(new TwitterLabException(TwitterLabService.CHAR_LIMIT_STR));
        Response testResponse = twitterLabResource.postTweet(post);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabService.CHAR_LIMIT_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    @Test
    public void testPostTweetException() throws TwitterLabException {
        User user = new User("test", "test", "");
        Post post = new Post("Test tweet", user, new Date(), "");
        when(mockedTwitterLabService.postTweet(post.getMessage())).thenThrow(new TwitterLabException(TwitterLabService.EXCEPTION_STR));
        Response testResponse = twitterLabResource.postTweet(post);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabService.EXCEPTION_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    @Test
    public void testPostNullTweet() throws TwitterLabException {
        // Ex: Invalid JSON passed in or null text
        User user = new User("test", "test", "");
        Post post = new Post(null, user, new Date(), "");
        when(mockedTwitterLabService.postTweet(post.getMessage())).thenThrow(new TwitterLabException(TwitterLabService.JSON_FORMAT_STR));
        Response testResponse = twitterLabResource.postTweet(post);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(TwitterLabService.JSON_FORMAT_STR, ((StatusMessage) testResponse.getEntity()).getStatus());
    }

}
