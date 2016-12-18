package tests;

import accountserver.database.tokens.Token;
import accountserver.database.tokens.TokenDao;
import accountserver.database.users.User;
import accountserver.database.users.UserDao;
import com.squareup.okhttp.Response;
import main.ApplicationContext;
import org.junit.Test;
import utils.json.JSONHelper;

import java.time.Duration;

import static javax.ws.rs.core.Response.Status;
import static org.junit.Assert.*;

/**
 * Created by user on 07.11.16.
 * <p>
 * Profile API tests
 */
public class TestProfileApi extends WebServerTest {
    @Test
    public void setNewNametest() {
        User u1 = new User("Cat", "123");
        User u2 = new User("Dog", "123");
        final String newname = "Pig";
        ApplicationContext.instance().get(UserDao.class).addUser(u1);
        ApplicationContext.instance().get(UserDao.class).addUser(u2);
        Token t = ApplicationContext.instance().get(TokenDao.class).generateToken(u1);
        assertNotNull(t);
        final String urlPostfix = "profile/name";
        try {
            Response actual = postRequest(urlPostfix, "name=", t.toString());
            assertEquals(Status.NOT_ACCEPTABLE, Status.fromStatusCode(actual.code()));
            actual = postRequest(urlPostfix, "name=" + u2.getName(), t.toString());
            assertEquals(Status.NOT_ACCEPTABLE, Status.fromStatusCode(actual.code()));
            actual = postRequest(urlPostfix, "name=" + newname, t.toString());
            assertEquals(Status.OK, Status.fromStatusCode(actual.code()));
            User u = ApplicationContext.instance().get(UserDao.class).getUserByName(u1.getName());
            assertNull(u);
            u = ApplicationContext.instance().get(UserDao.class).getUserByName(newname);
            assertNotNull(u);
            assertEquals(u1, u);
            u1 = u;
        } catch (Exception e) {
            fail(e.toString());
        } finally {
            ApplicationContext.instance().get(TokenDao.class).removeToken(t);
            ApplicationContext.instance().get(UserDao.class).removeUser(u1);
            ApplicationContext.instance().get(UserDao.class).removeUser(u2);
        }
    }

    @Test
    public void changepasstest() {
        User u1 = new User("Cat", "123");
        final String newpass = "478";
        ApplicationContext.instance().get(UserDao.class).addUser(u1);
        Token t = ApplicationContext.instance().get(TokenDao.class).generateToken(u1);
        assertNotNull(t);
        final String urlPostfix = "profile/changepass";
        try {
            Response actual = postRequest(urlPostfix, "oldpass=&newpass=145", t.toString());
            assertEquals(Status.NOT_ACCEPTABLE, Status.fromStatusCode(actual.code()));
            actual = postRequest(urlPostfix, "oldpass=245&newpass=145", t.toString());
            assertEquals(Status.UNAUTHORIZED, Status.fromStatusCode(actual.code()));
            actual = postRequest(urlPostfix, "oldpass=123&newpass=" + newpass, t.toString());
            assertEquals(Status.OK, Status.fromStatusCode(actual.code()));
            u1 = ApplicationContext.instance().get(UserDao.class).getUserByName(u1.getName());
            assertNotNull(u1);
            assertTrue(u1.validatePassword(newpass));
        } catch (Exception e) {
            fail(e.toString());
        } finally {
            ApplicationContext.instance().get(TokenDao.class).removeToken(t);
            assertNotNull(u1);
            ApplicationContext.instance().get(UserDao.class).removeUser(u1);
        }

    }

    @Test
    public void profileInfotest() {
        User u1 = new User("Cat", "123");
        u1.setEmail("qwerty");
        ApplicationContext.instance().get(UserDao.class).addUser(u1);
        Token t = ApplicationContext.instance().get(TokenDao.class).generateToken(u1);
        assertNotNull(t);
        final String urlPostfix = "profile/info";
        try {
            Response actual = getRequest(urlPostfix, "", t.toString());
            assertEquals(Status.OK, Status.fromStatusCode(actual.code()));
            User u2 = JSONHelper.fromJSON(actual.body().string(), User.class);
            assertNotNull(u2);
            assertEquals(u1.getName(), u2.getName());
            assertEquals(u1.getEmail(), u2.getEmail());
            assertTrue(Math.abs(u1.getRegistrationDate().getTime() - u2.getRegistrationDate().getTime())
                    < Duration.ofSeconds(1).toMillis());
        } catch (Exception e) {
            fail(e.toString());
        } finally {
            ApplicationContext.instance().get(TokenDao.class).removeToken(t);
            ApplicationContext.instance().get(UserDao.class).removeUser(u1);
        }

    }

    @Test
    public void profileUpdatetest() {
        User u1 = new User("Cat", "123");
        u1.setEmail("test@text");
        final String newEmail = "test@test";
        ApplicationContext.instance().get(UserDao.class).addUser(u1);
        Token t = ApplicationContext.instance().get(TokenDao.class).generateToken(u1);
        assertNotNull(t);
        final String urlPostfix = "profile/update";
        try {
            Response profileInfo = getRequest("profile/info", "", t.toString());
            assertEquals(Status.OK, Status.fromStatusCode(profileInfo.code()));
            User u2 = JSONHelper.fromJSON(profileInfo.body().string(), User.class);
            u2.setEmail(newEmail);
            Response actual = postRequest(urlPostfix, "data=" + JSONHelper.toJSON(u2), t.toString());
            assertEquals(Status.OK, Status.fromStatusCode(actual.code()));
            profileInfo = getRequest("profile/info", "", t.toString());
            u2 = JSONHelper.fromJSON(profileInfo.body().string(), User.class);
            assertEquals(newEmail, u2.getEmail());
            assertEquals(u1.getName(), u2.getName());
            assertTrue(Math.abs(u1.getRegistrationDate().getTime() - u2.getRegistrationDate().getTime())
                    < Duration.ofSeconds(1).toMillis());
        } catch (Exception e) {
            fail(e.toString());
        } finally {
            ApplicationContext.instance().get(TokenDao.class).removeToken(t);
            ApplicationContext.instance().get(UserDao.class).removeUser(u1);
        }

    }


}
