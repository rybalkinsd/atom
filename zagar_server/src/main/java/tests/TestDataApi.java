package tests;

import accountserver.api.data.DataApi;
import accountserver.database.tokens.Token;
import accountserver.database.tokens.TokenDao;
import accountserver.database.users.User;
import accountserver.database.users.UserDao;
import com.squareup.okhttp.Response;
import main.ApplicationContext;
import org.junit.Test;
import utils.json.JSONHelper;

import static javax.ws.rs.core.Response.Status;
import static org.junit.Assert.*;

/**
 * Created by user on 07.11.16.
 * <p>
 * Data API tests
 */
public class TestDataApi extends WebServerTest {
    @Test
    public void testdata() {
        User[] users = new User[]{new User("Cat", "123"), new User("Dog", "456"), new User("Pig", "458")};
        final String urlPostfix = "data/users";
        for (User u : users) {
            ApplicationContext.instance().get(UserDao.class).addUser(u);
            Token t = ApplicationContext.instance().get(TokenDao.class).generateToken(u);
            assertNotNull(t);
        }
        try {
            Response response = getRequest(urlPostfix, "", "");
            assertEquals(Status.OK, Status.fromStatusCode(response.code()));
            String rawJson = response.body().string();
            DataApi.UserInfo ui = JSONHelper.fromJSON(rawJson, DataApi.UserInfo.class);
            assertNotNull(ui);
            for (User u : users) {
                boolean contains = false;
                for (User deserU : ui.users) {
                    if (deserU.getName().equals(u.getName())) {
                        contains = true;
                        break;
                    }
                }
                assertTrue(contains);
            }
        } catch (Exception e) {
            fail(e.toString());
        } finally {
            for (User u : users) {
                ApplicationContext.instance().get(TokenDao.class).removeToken(u);
                ApplicationContext.instance().get(UserDao.class).removeUser(u);
            }
        }
    }
}
