package ru.atom;

/**
 * Created by vladfedorenko on 26.03.17.
 */

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Before;
import ru.atom.auth.AuthFilter;
import ru.atom.client.RestClient;
import ru.atom.client.RestClientImpl;
import ru.atom.Server;
import ru.atom.auth.ApiServlet;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertArrayEquals;

public class AuthServletTest {
    private RestClient client = new RestClientImpl();
    private Long adminToken = 0L;
    private String adminLogin = "admin";
    private String adminPass = "admin";


    @BeforeClass
    public static void init() throws Exception {
        try {
            ApiServlet.start(true);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @AfterClass
    public static void destroy() {
        ApiServlet.finish();
    }


    @Test
    public void testCorrectRegistration() throws Exception {
        int code = client.register("testUser1", "testUser1");
        assertEquals(200, code);
    }

    @Test
    public void testRegistrationWithEmptyLogin() throws Exception {
        int code = client.register("", "testUser1");
        assertEquals(400, code);
    }

    @Test
    public void testRegistrationWithEmptyPass() throws Exception {
        int code = client.register("user", "");
        assertEquals(400, code);
    }


    @Test
    public void testRegstrationWithExistingLogin() throws Exception {
        int code = client.register(adminLogin, adminPass);
        assertEquals(406, code);
    }

    @Test
    public void testSameLoginBearer() throws Exception {
        Long newAdminToken = Long.parseLong(client.login(adminLogin, adminPass)
                .body()
                .string()
                .trim());
        assertEquals(adminToken, java.util.Optional.ofNullable(newAdminToken).get());
    }

    @Test
    public void testLoginWithWrongPass() throws Exception {
        int codeFromLogin = client.login(adminLogin, adminPass + "123!").code();
        assertNotEquals(400, codeFromLogin);
    }

    @Test
    public void testLogout() throws Exception {
        client.register("ImHereForTestLogout","ImHereForTestLogout");
        okhttp3.Response loginResponse = client.login("ImHereForTestLogout","ImHereForTestLogout");
        List<String> beforeList = client.getOnline();
        Long token = Long.parseLong(loginResponse
                .body()
                .string().trim());
        client.logout(token);
        List<String> afterList = client.getOnline();
        beforeList.remove("ImHereForTestLogout");
        beforeList.sort(String.CASE_INSENSITIVE_ORDER);
        afterList.sort(String.CASE_INSENSITIVE_ORDER);
        assertArrayEquals(beforeList.toArray(), afterList.toArray());
    }

    @Test
    public void testGetUsers() throws Exception {
        List<String> usersBefore = client.getOnline();
        client.register("user1234","user1234");
        client.login("user1234","user1234");
        List<String> usersAfter = client.getOnline();
        usersBefore.add("user1234");
        usersAfter.sort(String.CASE_INSENSITIVE_ORDER);
        usersBefore.sort(String.CASE_INSENSITIVE_ORDER);
        assertArrayEquals(usersAfter.toArray(), usersBefore.toArray());
    }





}
