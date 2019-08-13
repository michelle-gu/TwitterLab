package com.khoros.twitter.core;

import com.khoros.twitter.core.Message;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class TestMessage {

    @Test
    public void testSetText() {
        Message message = new Message();
        message.setText("Test");
        assertEquals("Test", message.getText());
    }

    @Test
    public void testGetText() {
        Message message = new Message("Test");
        assertEquals("Test", message.getText());
    }

}
