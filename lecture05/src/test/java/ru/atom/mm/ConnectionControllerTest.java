package ru.atom.mm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.atom.mm.controller.ConnectionController;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import(Config.class)
public class ConnectionControllerTest {
    @Autowired
    private ConnectionController connectionHandler;

    @Test
    public void connect() throws Exception {
        assertTrue(connectionHandler.list().isEmpty());

        connectionHandler.connect(1, "a");
        connectionHandler.connect(2, "b");
        connectionHandler.connect(3, "c");

        assertTrue(
                connectionHandler.list()
                        .equals("Connection{playerId=1, name='a'}" +
                                "Connection{playerId=2, name='b'}" +
                                "Connection{playerId=3, name='c'}"));
    }

    @Test
    public void list() throws Exception {
        assertTrue(connectionHandler.list().isEmpty());
    }

}