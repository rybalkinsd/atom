package ru.atom.boot.mm;

import org.junit.Ignore;
import org.junit.Test;
import ru.atom.thread.mm.ConnectionQueue;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class ConnectionControllerTest {

    @Test
    public void connect() throws Exception {
        ConnectionController connectionHandler = new ConnectionController();
        ConnectionQueue.getInstance().clear();
        assertEquals(connectionHandler.list(), "[]");

        connectionHandler.connect(1, "a");
        connectionHandler.connect(2, "b");
        connectionHandler.connect(3, "c");

        assertThat(connectionHandler.list()).isNotEmpty();
    }

    @Test
    public void list() throws Exception {
        ConnectionController connectionHandler = new ConnectionController();
        connectionHandler.connect(1, "a");
        connectionHandler.connect(2, "b");
        connectionHandler.connect(3, "c");
        assertEquals("[{\"playerId\":1,\"name\":\"a\"},{\"playerId\":2,\"name\":\"b\"},{\"playerId\":3,\"name\":\"c\"}]", new ConnectionController().list());
    }

}