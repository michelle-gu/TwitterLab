package com.khoros.twitter.resources;

import com.khoros.twitter.core.Message;
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
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                postTweetResource.postTweet(message).getStatus());
    }

    @Test
    public void testPostValidTweet() {
        Message message = new Message("Test tweet");
        assertEquals(Response.Status.OK.getStatusCode(), postTweetResource.postTweet(message).getStatus());
    }

    @Test
    public void testPostLongTweet() {
        Message message = new Message(StringUtils.repeat("*", CHAR_LIMIT + 1));
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                postTweetResource.postTweet(message).getStatus());
    }

    @Test
    public void testPostTweetException() {
        Message message = new Message("Test tweet");
        when(postTweetResource.postTweet(message)).thenThrow(new TwitterException("Test exception"));
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                postTweetResource.postTweet(message).getStatus());
    }

    @Test
    public void testPostNullTweet() {
        // Ex: Invalid JSON passed in
        Message message = new Message(null);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                postTweetResource.postTweet(message).getStatus());
    }

}
