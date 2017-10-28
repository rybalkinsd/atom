package ru.atom.boot.mm;

import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class ConnectionControllerTest {

    @Test
    public void connect() throws Exception {
        ConnectionController connectionHandler = new ConnectionController();
        assertThat(connectionHandler.list()).isEmpty();

        connectionHandler.connect(1, "a");
        connectionHandler.connect(2, "b");
        connectionHandler.connect(3, "c");

        assertThat(connectionHandler.list()).isNotEmpty();
    }

    @Test
    public void list() throws Exception {
        ConnectionQueue.getInstance().clear();
        ConnectionController connectionHandler = new CoonnectionController();
        assertThat(connectionHandler.list()).isEmpty();
        connectionHandler.connect(1, "a");
        connectionHandler.connect(2, "b");
        connectionHandler.connect(3, "c");
        Thread.sleep(5000);

        assertEquals("a, b, c", new ConnectionController().list());
    }

}