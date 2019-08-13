package com.khoros.twitter;

import com.khoros.twitter.core.Message;
import com.khoros.twitter.core.StatusMessage;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class TestStatusMessage {

    @Test
    public void testSetStatus() {
        StatusMessage statusMessage = new StatusMessage();
        statusMessage.setStatus("Test");
        assertEquals("Test", statusMessage.getStatus());
    }

    @Test
    public void testGetStatus() {
        Message message = new Message("Test");
        assertEquals("Test", message.getText());
    }

}
