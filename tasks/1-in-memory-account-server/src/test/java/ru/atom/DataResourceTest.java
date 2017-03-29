package ru.atom;

import org.junit.Before;
import org.junit.Test;
import ru.atom.authserver.AuthResource;
import ru.atom.authserver.DataResource;
import ru.atom.entities.TokenContainer;

import static org.junit.Assert.assertTrue;

/**
 * Created by ikozin on 29.03.17.
 */
public class DataResourceTest {
    @Before
    public void clean() throws Exception {
        TokenContainer.clean();
    }

    @Test
    public void showNoUsers() throws Exception {
        assertTrue(DataResource.users().getEntity().equals(
                "{\"users\":\"[]\"}"
        ));
    }

    @Test
    public void showUsers() throws Exception {
        String username = "user";
        String password = "passwd";
        AuthResource.register(username, password);
        AuthResource.login(username, password);
        AuthResource.register(username + "1", password + "1");
        AuthResource.login(username + "1", password + "1");
        assertTrue(DataResource.users().getEntity().equals(
                "{\"users\":\"[" + username + "1, " + username + "]\"}"
        ));
    }
}
