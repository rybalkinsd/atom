package ru.atom.server;

import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

/**
 * Created by Ксения on 28.03.2017.
 */
public class AuthResourceTest {

    @Test
    public void register() throws Exception {

        Response response = AuthResource.register("Peter", "OPX");
        String body = response.getEntity().toString();
        Assert.assertTrue(response.getStatus() == 200 || body.equals("New user Peter is registrated with id 0"));

        response = AuthResource.register("Peter", "OPX");
        body = response.getEntity().toString();
        Assert.assertTrue(response.getStatus() == 400 || body.equals("User Peter already registered"));

        response = AuthResource.register("Alice", "23");
        body = response.getEntity().toString();
        Assert.assertTrue(response.getStatus() == 400 || body.equals("Too short password"));

        response = AuthResource.register("Alice", "2356");
        body = response.getEntity().toString();
        Assert.assertTrue(response.getStatus() == 200 || body.equals("New user Alice is registrated with id 1"));
    }

    @Test
    public void login() throws Exception {
        AuthResource.register("Leon", "OPX");

        Response response = AuthResource.login("Leon", "hoij");
        String body = response.getEntity().toString();
        Assert.assertTrue(response.getStatus() == 400 || body.equals("Wrong password for Leon"));

        response = AuthResource.login("Leon", "OPX");
        body = response.getEntity().toString();
        Assert.assertTrue(response.getStatus() == 200 || body.startsWith("User Leon logined with new token"));

        response = AuthResource.login("Leon", "OPX");
        body = response.getEntity().toString();
        Assert.assertTrue(response.getStatus() == 400 || body.startsWith("User Leon already logined with token"));

        response = AuthResource.login("Alice", "2368");
        body = response.getEntity().toString();
        Assert.assertTrue(response.getStatus() == 400 || body.startsWith("User Alice isn't registered"));
    }

    @Test
    public void logout() throws Exception {
        AuthResource.register("Sara", "OPX");
        AuthResource.login("Sara", "OPX");
        Response response = AuthResource.logout(Long.toString(AuthResource.getTokenStore().getToken(0).getToken() + 1));
        Assert.assertTrue(response.getStatus() == 400);

        response = AuthResource.logout(null);
        Assert.assertTrue(response.getStatus() == 400);

        response = AuthResource.logout(AuthResource.getTokenStore().getToken(0).getToken().toString());
        String body = response.getEntity().toString();
        Assert.assertTrue(response.getStatus() == 200 || body.equals("User Peter is logouted"));

    }


}