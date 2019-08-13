package com.khoros.twitter.resources;

import com.khoros.twitter.resources.GetTimelineResource;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import javax.ws.rs.core.Response;

public class TestGetTimelineResource {

    private GetTimelineResource getTimelineResource;
    private Twitter mockedTwitter;

    @Before
    public void setUp() {
        mockedTwitter = mock(Twitter.class);
        getTimelineResource = new GetTimelineResource(mockedTwitter);
    }

    @Test
    public void testGetTimeline() {
        assertEquals(Response.Status.OK.getStatusCode(), getTimelineResource.getTimeline().getStatus());
    }

    @Test
    public void testGetTimelineException() {
        when(getTimelineResource.getTimeline()).thenThrow(new TwitterException("Test exception"));
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                getTimelineResource.getTimeline().getStatus());
    }

}
