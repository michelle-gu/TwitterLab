package com.khoros.twitter;

import com.khoros.twitter.resources.GetTimelineResource;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import twitter4j.Twitter;

import javax.ws.rs.core.Response;

public class TestGetTimelineResource {

    private GetTimelineResource mockedGetTimelineResource;
    private Twitter mockedTwitter;

    @Before
    public void setUp() {
        mockedTwitter = mock(Twitter.class);
        mockedGetTimelineResource = new GetTimelineResource(mockedTwitter);
    }

    @Test
    public void testGetTimeline() {
        assertEquals(Response.Status.OK.getStatusCode(), mockedGetTimelineResource.getTimeline().getStatus());
    }

    // TODO: Test for invalid credentials case, twitter exception case

}
