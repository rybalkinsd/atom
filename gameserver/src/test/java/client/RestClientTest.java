package client;

import model.UserStore;
import org.junit.*;
import server.api.ApiServlet;

import java.util.List;

import static org.junit.Assert.*;


public class RestClientTest {

    private static IRestClient client = new RestClient();
    private Long token;
    private static String user = "testok";
    private static String password = "test";
    private static String user2 = "testerok";
    private static String password2 = "test";

    @BeforeClass
    public static void start() throws Exception {
        client.register(user, password);
        client.register(user2, password2);
    }

    @Test
    public void loginRegistered() throws Exception {
        token = client.login(user, password);
        assertNotNull(token);
        System.out.print(token);
    }

    @Test
    public void checkDifferentTokensOfDifferentUsers() {
        token = client.login(user, password);
        Long token2 = client.login(user2, password2);
        assertNotEquals(token,token2);
    }

    @Test
    public void checkTokenStabilityForOneUser() {
        token = client.login(user, password);
        Long token2 = client.login(user, password);
        assertEquals(token, token2);
    }

    @Test
    public void loginNotRegistered() throws Exception {
        token = client.login("user", "password");
        assertNull(token);
    }

    @Test
    public void getUsersProper() throws Exception {
        token =  client.login(user, password);
        List result = client.getUsers(token);
        assertNotNull(result);
    }

    @Test
    public void getUsersNotProper() throws Exception {
        List result = client.getUsers(7234726354L);
        assertNull(result);
    }

    @Test
    public void changeNameProper() throws Exception {
        token =  client.login(user, password);
        boolean result = client.changeName(token, "tester");
        boolean result2 = client.changeName(token, user);
        assertTrue(result2);
        assertTrue(result);
    }

    @Test
    public void changeNameNotProper() throws Exception {
        boolean result = client.changeName(1123123L, "test");
        assertFalse(result);
    }

    @Test
    public void logoutProper() throws Exception {
        token =  client.login(user, password);
        boolean result = client.logout(token);
        assertTrue(result);
    }

    @Test
    public void logoutNotProper() throws Exception {
        assertFalse(client.logout(123123123123L));
    }
}