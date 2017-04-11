package ru.atom.http.server;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import ru.atom.dbhackaton.server.AuthFilter;
import ru.atom.dbhackaton.server.AuthService;
import ru.atom.dbhackaton.resource.Token;
import ru.atom.dbhackaton.resource.User;


import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by zarina on 26.03.17.
 */
public class AuthServiceTest extends JerseyTest {
    private String name1 = "initUser1";
    private String password1 = "initPass1";

    private String name2 = "initUser2";
    private String password2 = "initPass2";
    private String newPassword2 = "initNewPass2";
    private String token2;

    private String name3 = "initUser3";
    private String password3 = "initPass3";
    private String token3;

    private String pathRegister = "auth/register";
    private String pathLogin = "auth/login";
    private String pathChangePassword = "auth/changePassword";
    private String pathLogout = "auth/logout";

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(AuthService.class).register(AuthFilter.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        User user1 = new User(name1, password1);
        AuthService.users.put(user1.getName(), user1);

        User user2 = new User(name2, password2);
        Token token2 = new Token(user2);
        AuthService.users.put(user2.getName(), user2);
        AuthService.tokens.put(token2.getToken(), token2);
        this.token2 = AuthService.tokens.getToken(user2).toString();

        User user3 = new User(name3, password3);
        Token token3 = new Token(user3);
        AuthService.users.put(user3.getName(), user3);
        AuthService.tokens.put(token3.getToken(), token3);
        this.token3 = AuthService.tokens.getToken(user3).toString();
    }

    @Test
    public void register() {
        Response output = target(pathRegister).request()
                .post(Entity.entity("user=" + name1 + "&password=" + password1, MediaType.APPLICATION_FORM_URLENCODED));
        assertEquals("User exist", output.getStatus(), Response.Status.FORBIDDEN.getStatusCode());

        output = target(pathRegister).request()
                .post(Entity.entity("user=regUser1", MediaType.APPLICATION_FORM_URLENCODED));
        assertEquals("Password empty", output.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());

        output = target(pathRegister).request()
                .post(Entity.entity("user=regUser1&password=regPass1", MediaType.APPLICATION_FORM_URLENCODED));
        assertEquals("New first user", output.getStatus(), Response.Status.OK.getStatusCode());

        output = target(pathRegister).request()
                .post(Entity.entity("user=regUser2&password=regPass2", MediaType.APPLICATION_FORM_URLENCODED));
        assertEquals("New second user", output.getStatus(), Response.Status.OK.getStatusCode());
    }

    @Test
    public void login() {
        Response output = target(pathLogin).request()
                .post(Entity.entity("user=" + name1, MediaType.APPLICATION_FORM_URLENCODED));
        assertEquals("Password empty", output.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());

        output = target(pathLogin).request()
                .post(Entity.entity("user=loginUser1&password=loginPass1", MediaType.APPLICATION_FORM_URLENCODED));
        assertEquals("User not found", output.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());

        output = target(pathLogin).request()
                .post(Entity.entity("user=" + name1 + "&password=" + password2, MediaType.APPLICATION_FORM_URLENCODED));
        assertEquals("Invalid password", output.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());

        output = target(pathLogin).request()
                .post(Entity.entity("user=" + name1 + "&password=" + password1, MediaType.APPLICATION_FORM_URLENCODED));
        assertEquals("Login user with new token", output.getStatus(), Response.Status.OK.getStatusCode());

        output = target(pathLogin).request()
                .post(Entity.entity("user=" + name3 + "&password=" + password3, MediaType.APPLICATION_FORM_URLENCODED));
        assertEquals("Login user with old token", output.getStatus(), Response.Status.OK.getStatusCode());
        assertEquals("Check old token", output.readEntity(String.class), token3);
    }


    @Test
    public void changePassword() throws Exception {
        Response output = target(pathChangePassword).request()
                .post(Entity.entity("user=" + name2, MediaType.APPLICATION_FORM_URLENCODED));
        assertEquals("Password empty", output.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());

        output = target(pathChangePassword).request()
                .post(Entity.entity("user=chPassUser1&password=chOldPass1&new_password=chNewPass1",
                        MediaType.APPLICATION_FORM_URLENCODED));
        assertEquals("User not found", output.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());

        output = target(pathChangePassword).request()
                .post(Entity.entity("user=" + name2 + "&password=" + password1 + "&new_password=" + newPassword2,
                        MediaType.APPLICATION_FORM_URLENCODED));
        assertEquals("Invalid password", output.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());

        output = target(pathChangePassword).request()
                .post(Entity.entity("user=" + name2 + "&password=" + password2 + "&new_password=" + newPassword2,
                        MediaType.APPLICATION_FORM_URLENCODED));
        assertEquals("Change password", output.getStatus(), Response.Status.OK.getStatusCode());

        output = target(pathLogin).request()
                .post(Entity.entity("user=" + name2 + "&password=" + password2,
                        MediaType.APPLICATION_FORM_URLENCODED));
        assertEquals("Check old password", output.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());

        output = target(pathLogin).request()
                .post(Entity.entity("user=" + name2 + "&password=" + newPassword2,
                        MediaType.APPLICATION_FORM_URLENCODED));
        assertEquals("Check new password", output.getStatus(), Response.Status.OK.getStatusCode());
    }

    @Test
    public void logout() throws Exception {
        Response output = target(pathLogout).request().post(null);
        assertEquals("Not Header", output.getStatus(), Response.Status.UNAUTHORIZED.getStatusCode());

        output = target(pathLogout).request().header(HttpHeaders.AUTHORIZATION, "Bearer 123").post(null);
        assertEquals("Invalid token", output.getStatus(), Response.Status.UNAUTHORIZED.getStatusCode());

        output = target(pathLogout).request().header(HttpHeaders.AUTHORIZATION, "Bearer " + token2).post(null);
        assertEquals("Logout user2", output.getStatus(), Response.Status.OK.getStatusCode());

        output = target(pathLogout).request().header(HttpHeaders.AUTHORIZATION, "Bearer " + token2).post(null);
        assertEquals("Double logout", output.getStatus(), Response.Status.UNAUTHORIZED.getStatusCode());
    }

}
