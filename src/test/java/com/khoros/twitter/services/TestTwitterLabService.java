package com.khoros.twitter.services;

import com.khoros.twitter.TwitterLabConfiguration;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TestTwitterLabService {

    private TwitterLabService twitterLabService1;
    private TwitterLabService twitterLabService2;

    @Before
    public void setUp() {
        TwitterLabConfiguration config1 = new TwitterLabConfiguration();
        TwitterLabConfiguration config2 = new TwitterLabConfiguration();
        twitterLabService1 = TwitterLabService.getInstance(config1);
        twitterLabService2 = TwitterLabService.getInstance(config2);
    }

    @Test
    public void testIsSingleton() {
        assertEquals(twitterLabService1, twitterLabService2);
    }


}
