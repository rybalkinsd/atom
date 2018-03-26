package ru.atom.mm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.atom.mm.controller.ConnectionController;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import(Config.class)
public class ConnectionControllerTest {

    @Autowired
    ApplicationContext appContext;

    @Test
    public void connect() throws Exception {
        ConnectionController connectionHandler = appContext.getBean(ConnectionController.class);
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