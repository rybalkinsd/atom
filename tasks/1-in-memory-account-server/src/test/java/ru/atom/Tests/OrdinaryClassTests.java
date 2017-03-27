package ru.atom.Tests;

import org.junit.Assert;
import org.junit.Test;
import serviceClasses.User;
import tokenClasses.Token;

/**
 * Created by kinetik on 27.03.17.
 */
public class OrdinaryClassTests {

    @Test
    public void userTest () {
        User user = new User("user","pwd");
        Token token = new Token(10L);
        user.setToken(token);
        Assert.assertEquals("pwd", user.getPassword());
        Assert.assertEquals("user",user.getName());
        Assert.assertEquals(token, user.getToken());

        user.setPassword("");
        user.setToken(null);
        Assert.assertEquals(null, user.getToken());
        Assert.assertEquals("", user.getPassword());
    }

    @Test
    public void tokenTest () {
        Token token = new Token("token");
        Token tokenTwo = new Token(10L);
        Token tokenThree = new Token(new Token(new Integer(10)));
        Assert.assertEquals("token", token.getValue());
        Assert.assertEquals(10L, tokenTwo.getValue());
        Assert.assertEquals(new Token(new Integer(10)), tokenThree.getValue());

        tokenTwo.setValue(20L);
        Assert.assertEquals(20L, tokenTwo.getValue());
    }
}
