package ru.atom.dbhackaton.server;


import org.junit.Assert;
import org.junit.Test;
import ru.atom.dbhackaton.server.mm.Connection;

public class ConnectionTest {
    @Test
    public void mainTest() {
        Connection connection1 = new Connection("123");
        Connection connection2 = new Connection("123");
        Assert.assertTrue(connection1.equals(connection2));
        Assert.assertTrue(connection1.hashCode() == (connection2.hashCode()));
    }
}
