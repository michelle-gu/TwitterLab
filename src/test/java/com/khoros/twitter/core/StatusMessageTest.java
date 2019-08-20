package com.khoros.twitter.core;

import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class StatusMessageTest {

    @Test
    public void testSetStatus() {
        StatusMessage statusMessage = new StatusMessage();
        statusMessage.setStatus("Test");
        assertEquals("Test", statusMessage.getStatus());
    }

    @Test
    public void testGetStatus() {
        StatusMessage message = new StatusMessage("Test");
        assertEquals("Test", message.getStatus());
    }

}
