package com.khoros.twitter.resources;

import com.khoros.twitter.core.Message;
import com.khoros.twitter.services.TwitterLabService;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

public class TestTwitterLabResource {

    private TwitterLabResource twitterLabResource;
    private TwitterLabService mockedTwitterLabService;

    @Before
    public void setUp() {
        mockedTwitterLabService = mock(TwitterLabService.class);
        twitterLabResource = new TwitterLabResource(mockedTwitterLabService);
    }

    @Test
    public void testGetTimeline() {
        when(mockedTwitterLabService.getTimeline()).thenReturn(Response.status(200).build());
        Response testResponse = twitterLabResource.getTimeline();
        assertEquals(Response.Status.OK.getStatusCode(), testResponse.getStatus());
        verify(mockedTwitterLabService).getTimeline();
    }

    // PostTweet tests
    @Test
    public void testPostTweet() {
        Message message = new Message("test");
        when(mockedTwitterLabService.postTweet(message)).thenReturn(Response.status(200).build());
        Response testResponse = twitterLabResource.postTweet(message);
        assertEquals(Response.Status.OK.getStatusCode(), testResponse.getStatus());
        verify(mockedTwitterLabService).postTweet(message);
    }

}
