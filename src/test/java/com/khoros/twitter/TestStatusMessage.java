package com.khoros.twitter;

import com.khoros.twitter.core.Message;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class TestStatusMessage {

    @Test
    public void testSetStatus() {
        Message message = new Message();
        message.setText("Test");
        assertEquals("Test", message.getText());
    }

    @Test
    public void testGetStatus() {
        Message message = new Message("Test");
        assertEquals("Test", message.getText());
    }

}
