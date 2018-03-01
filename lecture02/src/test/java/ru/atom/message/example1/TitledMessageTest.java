package ru.atom.message.example1;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class TitledMessageTest {

    @Test
    public void instance() throws Exception {
        TitledMessage message = new TitledMessage();

        assertTrue(message instanceof Message);
    }
}