package ru.atom.auth.server;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import okhttp3.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class AuthServerTest {
    @Test
    public void registerUser() throws Exception {
        AuthServer.serverRun();

        // register Maxim
        Response response1 = Client.register("Maxim", "1234");
        String body1 = response1.body().string();
        assertThat(response1.code() == 200 || body1.equals("User Maxim registered."), is(true));

        // dublicate register Maxim
        Response response2 = Client.register("Maxim", "1234");
        assertThat(response2.code() == 406, is(true));

        AuthServer.serverStop();
    }

    @Test
    public void loginUser() throws Exception {
        AuthServer.serverRun();

        // register Andrew
        Response response1 = Client.register("Andrew", "5678");

        // login registered user Andrew
        Response response2 = Client.login("Andrew", "5678");
        String body2 = response2.body().string();
        assertThat(response2.code() == 200 || (body2 != ""), is(true));
        // logout Andrew
        Response response3 = Client.logout(body2);

        // login unregistered user Roman
        Response response4 = Client.login("Roman", "90");
        assertThat(response4.code() == 401, is(true));

        AuthServer.serverStop();
    }

    @Test
    public void logOut() throws Exception {
        AuthServer.serverRun();

        // register user Roman
        Response response1 = Client.register("Roman", "90");
        String body1 = response1.body().string();

        // login user Roman
        Response response2 = Client.login("Roman", "90");
        String body2 = response2.body().string();
        assertThat(response2.code() == 200 || (body2 != ""), is(true));

        // logout loginned user Roman
        Response response3 = Client.logout(body2);
        assertThat(response3.code() == 200, is(true));

        // logout unloginned user Roman
        Response response4 = Client.logout(body2);
        assertThat(response4.code() == 401, is(true));

        AuthServer.serverStop();
    }

    @Test
    public void viewUsers() throws Exception {
        AuthServer.serverRun();

        Response response1 = Client.register("Sergey", "1234");
        Response response2 = Client.register("Andrew", "5678");
        Response response3 = Client.register("Roman", "90");

        // login registered user Sergey
        Response response4 = Client.login("Sergey", "1234");
        String body4 = response4.body().string();
        assertThat(response4.code() == 200 || (body4 != ""), is(true));

        // login registered user Roman
        Response response5 = Client.login("Roman", "90");
        String body5 = response5.body().string();
        assertThat(response5.code() == 200 || (body5 != ""), is(true));

        // login registered user Andrew
        Response response6 = Client.login("Andrew", "5678");
        String body6 = response6.body().string();
        assertThat(response6.code() == 200 || (body6 != ""), is(true));

        // view users form Andrew's token
        Response response7 = Client.viewUsers(body6);
        String body7 = response7.body().string();
        assertThat(response7.code() == 200
                || (body7 == "{users:[{\"name\":\"Andrew\"}," +
                "{\"name\":\"Roman\"}" +
                "{\"name\":\"Sergey\"}]}"), is(true));

        AuthServer.serverStop();
    }
}