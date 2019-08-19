package com.khoros.twitter.core;

import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class MessageTest {

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