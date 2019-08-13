package com.khoros.twitter.resources;

import com.khoros.twitter.core.StatusMessage;
import com.khoros.twitter.resources.GetTimelineResource;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import javax.ws.rs.core.Response;

public class TestGetTimelineResource {

    private static final String EXCEPTION_STR = "Error getting home timeline. Try again later!";

    private GetTimelineResource getTimelineResource;
    private Twitter mockedTwitter;

    @Before
    public void setUp() {
        mockedTwitter = mock(Twitter.class);
        getTimelineResource = new GetTimelineResource(mockedTwitter);
    }

    @Test
    public void testGetTimeline() {
        Response testResponse = getTimelineResource.getTimeline();
        assertEquals(Response.Status.OK.getStatusCode(), testResponse.getStatus());
    }

    @Test
    public void testGetTimelineException() {
        when(getTimelineResource.getTimeline()).thenThrow(new TwitterException("Test exception"));
        Response testResponse = getTimelineResource.getTimeline();
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), testResponse.getStatus());
        assertEquals(EXCEPTION_STR, ((StatusMessage)testResponse.getEntity()).getStatus());
    }

}
