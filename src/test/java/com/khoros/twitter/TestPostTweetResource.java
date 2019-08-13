package com.khoros.twitter;

import com.khoros.twitter.core.Message;
import com.khoros.twitter.resources.PostTweetResource;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import twitter4j.Twitter;

import javax.ws.rs.core.Response;

public class TestPostTweetResource {

    private PostTweetResource mockedPostTweetResource;
    private Twitter mockedTwitter;

    @Before
    public void setUp() {
        mockedTwitter = mock(Twitter.class);
        mockedPostTweetResource = new PostTweetResource(mockedTwitter);
    }

    @Test
    public void testPostEmptyTweet() {
        Message message = new Message("");
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                mockedPostTweetResource.postTweet(message).getStatus());
    }

    @Test
    public void testPostValidTweet() {
        Message message = new Message("Test tweet");
        assertEquals(Response.Status.OK.getStatusCode(), mockedPostTweetResource.postTweet(message).getStatus());
    }

    @Test
    public void testPostLongTweet() {
        Message message = new Message("Test Test Test Test Test Test Test Test Test Test Test Test Test Test " +
                "Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test " +
                "Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test" +
                " Test Test Test Test");
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                mockedPostTweetResource.postTweet(message).getStatus());
    }

    // TODO: Test for invalid JSON case
    // TODO: Test for invalid credentials case, twitter exception case

}
