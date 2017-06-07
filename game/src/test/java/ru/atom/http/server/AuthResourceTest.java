package ru.atom.http.server;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import ru.atom.http.server.dao.Database;
import ru.atom.http.server.dao.TokenDao;
import ru.atom.http.server.dao.UserDao;
import ru.atom.http.server.model.Token;
import ru.atom.http.server.model.User;


import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by zarina on 26.03.17.
 */
public class AuthResourceTest extends JerseyTest {
    private String name1 = "initUser1";
    private String password1 = "initPass1";

    private String name2 = "initUser2";
    private String password2 = "initPass2";
    private String newPassword2 = "initNewPass2";
    private String token2;

    private String name3 = "initUser3";
    private String password3 = "initPass3";
    private String token3;

    private String pathRegister = "register";
    private String pathLogin = "login";
    private String pathChangePassword = "changePassword";
    private String pathLogout = "logout";

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(AuthResource.class).register(AuthFilter.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        User user1 = new User().setName(name1).setPassword(password1);
        User user2 = new User().setName(name2).setPassword(password2);
        User user3 = new User().setName(name3).setPassword(password3);

        Database.setCfgResourceName("test.hibernate.cfg.xml");
        Database.setUp();

        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();

            UserDao.getInstance().insert(session, user1);
            UserDao.getInstance().insert(session, user2);
            UserDao.getInstance().insert(session, user3);
            session.flush();

            Token token2 = new Token().setUser(user2);
            Token token3 = new Token().setUser(user3);
            TokenDao.getInstance().insert(session, token2);
            TokenDao.getInstance().insert(session, token3);

            this.token2 = token2.getToken();
            this.token3 = token3.getToken();

            txn.commit();
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    @Override
    public void tearDown() throws Exception {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            TokenDao.getInstance().deleteAll(session);
            UserDao.getInstance().deleteAll(session);
            txn.commit();
        } catch (RuntimeException e) {
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
        super.tearDown();
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
