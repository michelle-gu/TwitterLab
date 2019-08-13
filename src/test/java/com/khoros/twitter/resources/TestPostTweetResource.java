package com.khoros.twitter.resources;

import com.khoros.twitter.core.Message;
import com.khoros.twitter.core.StatusMessage;
import com.khoros.twitter.resources.PostTweetResource;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import javax.ws.rs.core.Response;

public class TestPostTweetResource {

    private static final int CHAR_LIMIT = 280;
    private static final String EXCEPTION_STR = "Error posting tweet. Try again later!";
    private static final String CHAR_LIMIT_STR = "Invalid tweet: Tweet must be between 1-280 characters";
    private static final String JSON_FORMAT_STR = "Invalid JSON - use format: {\"text\":\"<your tweet here>\"}";

    private PostTweetResource postTweetResource;
    private Twitter mockedTwitter;

    @Before
    public void setUp() {
        mockedTwitter = mock(Twitter.class);
        postTweetResource = new PostTweetResource(mockedTwitter);
    }

    @Test
    public void testPostEmptyTweet() {
        Message message = new Message("");
        Response testResponse = postTweetResource.postTweet(message);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(CHAR_LIMIT_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    @Test
    public void testPostValidTweet() {
        Message message = new Message("Test tweet");
        assertEquals(Response.Status.OK.getStatusCode(), postTweetResource.postTweet(message).getStatus());
    }

    @Test
    public void testPostLongTweet() {
        Message message = new Message(StringUtils.repeat("*", CHAR_LIMIT + 1));
        Response testResponse = postTweetResource.postTweet(message);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(CHAR_LIMIT_STR, ((StatusMessage)testResponse.getEntity()).getStatus());

    }

    @Test
    public void testPostTweetException() {
        Message message = new Message("Test tweet");
        when(postTweetResource.postTweet(message)).thenThrow(new TwitterException("Test exception"));
        Response testResponse = postTweetResource.postTweet(message);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(EXCEPTION_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

    @Test
    public void testPostNullTweet() {
        // Ex: Invalid JSON passed in or null text
        Message message = new Message(null);
        Response testResponse = postTweetResource.postTweet(message);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(JSON_FORMAT_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

}
