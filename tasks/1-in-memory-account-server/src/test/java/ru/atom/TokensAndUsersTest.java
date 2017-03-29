package ru.atom;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Vlad on 28.03.2017.
 */
public class TokensAndUsersTest {
    @Test
    public void equalsUsers() {
        User user1 = new User("user_tom", "password_1");
        User user2 = new User("user_tom", "password_1");
        User user3 = new User("user_tom", "password_2");

        assertTrue(user1.equals(user3));
        assertTrue(user1.fullEquals(user2));
    }

    @Test
    public void equalsTokens() {
        Token token1 = new Token(new User("user_tom", "password_1"));
        Token token2 = new Token(new User("user_tom", "password_1"));
        Token token3 = new Token(new User("user_john", "password_1"));

        assertTrue(token1.equals(token2));
        assertTrue(!token1.equals(token3));
    }

    @Test
    public void tokensStorageMethods() {
        Token token1 = new Token(new User("user_tom", "password_1"));
        Token token2 = new Token(new User("user_tom", "password_2"));
        Token token3 = new Token(new User("user_john", "password_1"));

        TokensStorage.addToken(token1);
        TokensStorage.addToken(token2);
        TokensStorage.addToken(token3);

        assertTrue(TokensStorage.validateToken(token1));
        assertTrue(!TokensStorage.validateToken("1"));
    }

}
