package ru.atom;

import org.junit.Assert;
import org.junit.Test;
import ru.atom.client.User;
import ru.atom.token.Token;
import ru.atom.token.TokenManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by salvador on 03.04.17.
 */

public class TokenTest {

    @Test
    public void tokenManager() throws IOException {
        String name1 = "TestUser1";
        String password1 = "TestPassword1";

        String name2 = "TestUser2";
        String password2 = "TestPassword2";

        User user1 = new User(name1, password1);
        Token token1 = new Token(user1);

        User user2 = new User(name2, password2);
        Token token2 = new Token(user2);

        TokenManager tokens = new TokenManager();
        tokens.put(token1, user1);

        Assert.assertTrue(tokens.containsKey(token1));
        Assert.assertTrue(!tokens.containsKey(token2));

        tokens.remove(token1);
        Assert.assertTrue(!tokens.containsKey(token1));

        tokens.put(token1, user1);
        tokens.put(token2, user2);

        ArrayList<String> users = new ArrayList<>();
        users.add(user1.getName());
        users.add(user2.getName());
        Assert.assertTrue(users.containsAll(tokens.returnAllUsers()));
        Assert.assertTrue(tokens.returnAllUsers().containsAll(users));
    }

    @Test
    public void user() throws IOException {
        String name = "TestUser";
        String password = "TestPassword";
        User user = new User(name, password);
        Assert.assertTrue(user.toString().equals("User{" +
                "name='" + "TestUser" + '\'' +
                ", password='" + "TestPassword" + '\'' +
                '}'));
    }

    @Test
    public void token() throws IOException {
        String name = "TestUser";
        String password = "TestPassword";
        User user = new User(name, password);
        Token token = new Token(user);
        Assert.assertTrue(token.getToken() == -1384740496);
    }



}
