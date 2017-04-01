package ru.atom;

import org.junit.Assert;
import org.junit.Test;
import ru.atom.http.server.AuthService;
import ru.atom.resource.AllTokensHere;
import ru.atom.resource.HashCalculator;

import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SoHard {

    @Test

    public void hashTest() {
        String user = "name1";
        String password = new String("password1");
        Assert.assertFalse(Arrays.equals(HashCalculator.calcHash(password.getBytes()),
                (HashCalculator.calcHash(user.getBytes()))));
        Assert.assertTrue(Arrays.equals(HashCalculator.calcHash(password.getBytes()),
                (HashCalculator.calcHash(new String("password1").getBytes()))));
    }


    @Test
    public void registerTest() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String user1 = "name";
        String password1 = "password";
        AuthService authService1 = new AuthService();
        Response response1 = authService1.register(user1, password1);
        Assert.assertTrue(response1.getStatus() == 200);

        String user2 = "11111222223333344444";
        String password2 = "111";
        AuthService authService2 = new AuthService();
        Response response2 = authService2.register(user2, password2);
        Assert.assertTrue(response2.getStatus() == 400);

        String user3 = "";
        String password3 = "111";
        AuthService authService3 = new AuthService();
        Response response3 = authService3.register(user3, password3);
        Assert.assertTrue(response3.getStatus() == 400);

        String user4 = "name";
        String password4 = "password";
        AuthService authService4 = new AuthService();
        Response response4 = authService4.register(user4, password4);
        Assert.assertTrue(response4.getStatus() == 403);
    }

    @Test
    public void registerLoginLogoutUsersTest() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        AllTokensHere allTokensHere = new AllTokensHere();
        String user1 = "name1";
        String password1 = "password1";
        AuthService authService1 = new AuthService();
        authService1.register(user1, password1);

        String login1 = "name1";
        Response response1 = authService1.login(login1, password1);
        Assert.assertTrue(response1.getStatus() == 200);
        String token = allTokensHere.getToken(login1).toString();

        String password2 = "password2";
        Response response2 = authService1.login(login1, password2);
        Assert.assertTrue(response2.getStatus() == 400);

        Response response3 = authService1.users();
        Assert.assertTrue(response3.getStatus() == 200);

        String login2 = "name2";
        Response response4 = authService1.login(login2, password2);
        Assert.assertTrue(response4.getStatus() == 400);

        String token2 = "1";
        Response response5 = authService1.logout("Bearer " + token2);
        Assert.assertTrue(response5.getStatus() == 400);

        Response response6 = authService1.logout("Bearer " + token);
        Assert.assertTrue(response6.getStatus() == 200);

    }

}
