package ru.atom.boot.mm;

import org.junit.Ignore;
import org.junit.Test;
import ru.atom.thread.mm.ConnectionQueue;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConnectionControllerTest {

    @Test
    @Ignore
    public void connect() throws Exception {
        ConnectionController connectionHandler = new ConnectionController();
        assertThat(connectionHandler.list()).isEmpty();

        connectionHandler.connect(1, "a");
        connectionHandler.connect(2, "b");
        connectionHandler.connect(3, "c");

        assertThat(connectionHandler.list()).isNotEmpty();
    }

    @Test
    @Ignore
    public void list() throws Exception {
        ConnectionController connectionHandler = new ConnectionController();
        assertThat(connectionHandler.list()).isEmpty();

        connectionHandler.connect(1, "a");
        connectionHandler.connect(2, "b");
        connectionHandler.connect(3, "c");

        assertEquals("Connection{playerId=1, name='a'}" + '\n' +
                                "Connection{playerId=2, name='b'}" + '\n' +
                                "Connection{playerId=3, name='c'}" + '\n', connectionHandler.list());
    }

}