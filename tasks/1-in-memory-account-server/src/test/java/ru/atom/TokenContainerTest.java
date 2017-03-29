package ru.atom;

import org.junit.Before;
import org.junit.Test;
import ru.atom.entities.Token;
import ru.atom.entities.TokenContainer;
import ru.atom.entities.User;

import static org.junit.Assert.assertTrue;

/**
 * Created by ikozin on 29.03.17.
 */
public class TokenContainerTest {
    private final String username = "random";
    private final String password = "11111";
    private final User defaultUser = new User(username, password);
    private final Token correctToken = new Token(defaultUser);
    private final Token wrongToken = new Token(new User(username + "1", password + "1"));

    @Before
    public void clean() {
        TokenContainer.clean();
    }

    @Test
    public void addAndContainsTest() {
        TokenContainer.add(correctToken, defaultUser);
        assertTrue(TokenContainer.containsUser(username));
        assertTrue(TokenContainer.containsToken(correctToken.getValue()));
        assertTrue(!TokenContainer.containsToken(wrongToken.getValue()));
    }

    @Test
    public void validationTest() {
        TokenContainer.add(correctToken, defaultUser);
        assertTrue(TokenContainer.validate(correctToken.getValue()));
        assertTrue(!TokenContainer.validate(wrongToken.getValue()));
        TokenContainer.add(wrongToken, new User("abc", "def"));
        assertTrue(!TokenContainer.validate(wrongToken.getValue()));
    }

    @Test
    public void removalTest() {
        TokenContainer.add(correctToken, defaultUser);
        assertTrue(TokenContainer.containsToken(correctToken.getValue()));
        assertTrue(TokenContainer.containsUser(defaultUser.getUsername()));
        TokenContainer.remove(correctToken.getValue());
        assertTrue(!TokenContainer.containsToken(correctToken.getValue()));
        assertTrue(!TokenContainer.containsUser(defaultUser.getUsername()));
    }

    @Test
    public void gettersTest() {
        TokenContainer.add(correctToken, defaultUser);
        TokenContainer.add(wrongToken, new User(username + "1", password + "1"));
        assertTrue(TokenContainer.get(correctToken.getValue()).equals(defaultUser));
        assertTrue(TokenContainer.get(wrongToken.getValue())
                .equals(new User(username + "1", password + "1")));
        assertTrue(TokenContainer.getAllUsernames().contains(username)
                && TokenContainer.getAllUsernames().contains(username + "1"));
    }
}
