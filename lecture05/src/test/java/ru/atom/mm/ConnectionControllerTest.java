package ru.atom.mm;

import org.junit.Ignore;
import org.junit.Test;
import ru.atom.mm.controller.ConnectionController;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Ignore
public class ConnectionControllerTest {

    @Test
    public void connect() throws Exception {
        ConnectionController connectionHandler = new ConnectionController();
        assertTrue(connectionHandler.list().isEmpty());

        connectionHandler.connect(1, "a");
        connectionHandler.connect(2, "b");
        connectionHandler.connect(3, "c");

        assertFalse(connectionHandler.list().isEmpty());
    }

    @Test
    public void list() throws Exception {
        assertTrue(false);
    }

}