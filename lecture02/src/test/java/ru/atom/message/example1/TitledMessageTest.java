package ru.atom.message.example1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TitledMessageTest {

    @Test
    public void instance() throws Exception {
        TitledMessage message = new TitledMessage();

        assertTrue(message instanceof Message);
    }
}