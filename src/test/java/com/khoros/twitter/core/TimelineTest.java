package com.khoros.twitter.core;

import org.junit.Test;
import twitter4j.Status;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TimelineTest {

    @Test
    public void testGetTimeline() {
        List<Status> statusList = new ArrayList<Status>();
        Timeline timeline = new Timeline(statusList);
        assertEquals(statusList, timeline.getTimeline());
    }

}
