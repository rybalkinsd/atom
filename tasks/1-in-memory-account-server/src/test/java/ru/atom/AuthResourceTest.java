package ru.atom;

import org.junit.Before;
import org.junit.Test;
import ru.atom.authserver.AuthResource;
import ru.atom.entities.Token;
import ru.atom.entities.TokenContainer;
import ru.atom.entities.User;

import static org.junit.Assert.assertTrue;

public class AuthResourceTest {
    private final String username = "ivanov";
    private final String username2 = "johnson";
    private final String password = "qwerty123";
    private final String password2 = "321ytrewq";
    private final String correctToken = new Token(new User(username, password)).toString();
    private final String wrongToken = new Token(new User(username, password + "1")).toString();

    @Before
    public void clean() {
        TokenContainer.clean();
    }

    @Test
    public void checkIfAlreadyRegistered() throws Exception {
        AuthResource.register(username, password);
        assertTrue(AuthResource.register(username, password).getStatus() == 400);
    }

    @Test
    public void checkIfRegisteredAtLogin() throws Exception {
        assertTrue(AuthResource.login(username2, password).getStatus() == 400);
        AuthResource.register(username2, password);
        assertTrue(AuthResource.login(username2, password).getStatus() == 200);
    }

    @Test
    public void checkIfPasswordIsCorrect() throws Exception {
        AuthResource.register(username, password);
        assertTrue(AuthResource.login(username, password2).getStatus() == 400);
    }

    @Test
    public void doubleLogin() throws Exception {
        AuthResource.register(username, password);
        AuthResource.login(username, password);
        assertTrue(AuthResource.login(username, password).getEntity()
                .equals(new Token(new User(username, password)).toString()));
    }

    @Test
    public void logoutNotLoggedIn() throws Exception {
        assertTrue(AuthResource.logout(wrongToken).getStatus() == 401);
    }

    @Test
    public void logoutLoggedIn() throws Exception {
        AuthResource.register(username, password);
        AuthResource.login(username, password);
        assertTrue(AuthResource.logout(correctToken).getStatus() == 200);
    }
}
