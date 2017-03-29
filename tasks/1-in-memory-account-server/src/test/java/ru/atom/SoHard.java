package ru.atom;

import org.junit.Assert;
import org.junit.Test;
import ru.atom.http.server.Service;

import static org.junit.Assert.assertTrue;

public class SoHard {

    @Test
    public void toPass() throws Exception {
        Service srv = new Service();
        srv.register("u1", "p1");
        String token = srv.login("u1", "p1").getEntity().toString();
        Assert.assertEquals(token, srv.login("u1", "p1").getEntity().toString());
        token = srv.userList().getEntity().toString();
        srv.login("u2", "p2");
        Assert.assertEquals(token, srv.userList().getEntity().toString());
        String userList = srv.userList().getEntity().toString();

        srv.register("u2", "p2");
        token = srv.login("u2", "p2").getEntity().toString();
        Assert.assertNotEquals(userList, srv.userList().getEntity().toString());
        userList = srv.userList().getEntity().toString();

        srv.logout(token);

        Assert.assertNotEquals(userList, srv.userList().getEntity().toString());

        String token2 = srv.login("u2", "p2").getEntity().toString();

        Assert.assertNotEquals(token, token2);
        userList = srv.userList().getEntity().toString();
        srv.logout(token);

        Assert.assertEquals(userList, srv.userList().getEntity().toString());

    }
}
