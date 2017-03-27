package ru.atom.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.User;
import tokens.Token;
import tokens.TokenManager;

/**
 * Created by kinetik on 27.03.17.
 */
public class TokenManagerTests {

    private TokenManager tokenManager;

    @Before
    public void initializer() {
        this.tokenManager = new TokenManager();
    }

    @Test
    public void putUsers() {
        User testUserOne = new User("testUserOne","pwd");
        User testUserTwo = new User("testUserOne","pwd");
        User testUserThree = new User("testUserThree", "pwd");
        User testUserFour = new User("testUserOne", "");
        Token tokenOne = this.tokenManager.getNewToken(testUserOne);
        Token tokenTwo = this.tokenManager.getNewToken(testUserTwo);
        Token tokenThree = this.tokenManager.getNewToken(testUserThree);
        Token tokenFour = this.tokenManager.getNewToken(testUserFour);
        Assert.assertFalse(tokenOne.equals(tokenTwo));
        Assert.assertFalse(tokenOne.equals(tokenThree));
        Assert.assertFalse(tokenOne.equals(tokenFour));
        Assert.assertFalse(tokenTwo.equals(tokenThree));
        Assert.assertFalse(tokenTwo.equals(tokenFour));
        Assert.assertFalse(tokenThree.equals(tokenFour));
    }

    @Test
    public void validateTokenTest() {
        String tokenOne = "65793939";
        String tokenTwo = "Name";
        User testFive = new User("testValidate","");
        Token tokenLast = this.tokenManager.getNewToken(testFive);
        Assert.assertFalse(this.tokenManager.validateToken(tokenOne));
        Assert.assertFalse(this.tokenManager.validateToken(tokenTwo));
        Assert.assertTrue(this.tokenManager.validateToken(tokenLast.getValue().toString()));
    }

    @Test
    public void logoutTest() {
        User testSix = new User("testSix","");
        Token token = this.tokenManager.getNewToken(testSix);
        Assert.assertTrue(this.tokenManager.getUserByToken((Long) token.getValue()).equals(testSix));
        this.tokenManager.logout((Long) token.getValue());
        Assert.assertEquals(null, this.tokenManager.getUserByToken((Long) token.getValue()));
    }
}
