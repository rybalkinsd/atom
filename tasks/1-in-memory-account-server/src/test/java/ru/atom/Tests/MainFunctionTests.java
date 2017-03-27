package ru.atom.Tests;

import okhttp3.Response;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import ru.atom.RestClient.ClientImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kinetik on 26.03.17.
 */
public class MainFunctionTests {
    /*
    @Test
    public void getUsersTests () throws IOException {
        ClientImpl client = new ClientImpl();
        ArrayList<String> arrOfUser = new ArrayList<>();
        String[] names = {"User1", "User2", "User3"};
        for (String name: names) {
            client.register(name, name);
            client.login(name, name);
            arrOfUser.add(name);
        }
        Response registrate = client.register("getUserTest", "");
        Response getUsersOut = client.getUsers("getUserTest");
        Response login = client.login("getUserTest", "");
        Response getUserOne = client.getUsers("getUserTest");
        String users = getUserOne.body().string();
        HashMap<String, ArrayList<String>> forJson = new HashMap<>();
        forJson.put("Users", userLogins);
        String jsonString = mapper.writeValueAsString(forJson);

    } */

    @Test
    public void registreOneTest() throws IOException {
        ClientImpl client = new ClientImpl();
        Response response = client.register("admin", "admin");
        System.out.println(response.body().string());
        Assert.assertEquals(200, response.code());
    }

    @Test
    public void registreBadLoginTests() {
        ClientImpl client = new ClientImpl();
        Response responseLongName = client.register("ochenDlinnyImaOver30pointsThereYep", "");
        Response responseNullName = client.register("", "");
        Assert.assertEquals(400, responseLongName.code());
        Assert.assertEquals(400, responseNullName.code());
    }

    @Test
    public void registreMoreTest() {
        ClientImpl client = new ClientImpl();
        Response response = client.register("adminMore", "admin");
        Response responseTwo = client.register("adminMore", "admin");
        Assert.assertEquals(200, response.code());
        Assert.assertEquals(400, responseTwo.code());
    }

    @Test
    public void loginTests() throws IOException {
        ClientImpl client = new ClientImpl();
        Response response = client.register("loginTest", "loginTest");
        Assert.assertEquals(200, response.code());
        Response responseLogin = client.login("loginTest", "loginTest");
        Assert.assertEquals(200, responseLogin.code());
    }

    @Test
    public void loginBadInputTests() {
        ClientImpl clientTest = new ClientImpl();
        Response nullLogin = clientTest.login("", "");
        Response badLogin = clientTest.login("user", "passw");
        Response registration = clientTest.register("badLoginTest", "BadLoginTest");
        Response badPassword = clientTest.login("badLoginTest", "no");
        Assert.assertEquals(400, nullLogin.code());
        Assert.assertEquals(400, badLogin.code());
        Assert.assertEquals(200, registration.code());
        Assert.assertEquals(403, badPassword.code());
    }

    @Test
    public void loginManyTimes() throws IOException {
        ClientImpl client = new ClientImpl();
        Response registrate = client.register("userManyTimes", "pwd");
        Response loginOne = client.login("userManyTimes", "pwd");
        Long token = Long.parseLong(loginOne.body().string());
        Response loginTwo = client.login("userManyTimes", "pwd");
        Long tokenTwo = Long.parseLong(loginTwo.body().string());
        Assert.assertEquals(200, registrate.code());
        Assert.assertEquals(200, loginOne.code());
        Assert.assertEquals(200, loginTwo.code());
        Assert.assertEquals(token, tokenTwo);
    }

    @Test
    public void logoutTests() {
        ClientImpl client = new ClientImpl();
        Response registrate = client.register("logoutTest", "");
        Response logout = client.logout("logoutTest");
        Response login = client.login("logoutTest", "");
        Response logoutGood = client.logout("logoutTest");
        Response logoutOneMore = client.logout("logoutTest");
        Response logoutEmpty = client.logout("");
        Assert.assertEquals(200, registrate.code());
        Assert.assertEquals(500, logout.code());
        Assert.assertEquals(200, login.code());
        Assert.assertEquals(200, logoutGood.code());
        Assert.assertEquals(500, logoutOneMore.code());
        Assert.assertEquals(500, logoutEmpty.code());
    }
}