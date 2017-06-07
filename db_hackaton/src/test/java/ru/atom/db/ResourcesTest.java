package ru.atom.db;

import org.junit.Assert;
import org.junit.Test;
import ru.atom.dbhackaton.resource.User;
import ru.atom.dbhackaton.resource.Token;
import ru.atom.dbhackaton.resource.Result;

/**
 * Created by BBPax on 19.04.17.
 */
public class ResourcesTest {
    User user = new User().setId(0).setLogin("testUser1").setPassword("password");
    Token token = new Token().setId(3).setUser(user).setToken(0L);
    Result result = new Result().setGameId(7).setId(6).setUser(user).setScore(15);

    @Test
    public void passValidationTest() {
        Assert.assertTrue(user.validPassword("password"));
        System.out.println(user.getPassword());
        Assert.assertFalse(user.validPassword("sdjhfbsjgfjs"));
        Assert.assertNotEquals("password", user.getPassword());
    }

    @Test
    public void getTest() {
        Assert.assertEquals(user, token.getUser());
        Assert.assertEquals(user, result.getUser());
    }
}
