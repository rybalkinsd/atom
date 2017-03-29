package ru.atom;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import ru.atom.persons.Token;
import ru.atom.persons.TokenStorage;
import ru.atom.persons.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BBPax on 29.03.17.
 */
public class PersonsTest {
    private User myUser = new User("Vladislav", "very11Complicated11Pass");
    private User myFriend = new User("Friend", "qwerty");
    private Token testToken = new Token("1234567890");

    @Test
    public void userTest() {
        Assert.assertEquals("Vladislav", myUser.getUserName());
        Assert.assertEquals(new User("Vladislav", "very11Complicated11Pass"), myUser);
        //Assert.assertEquals("very11Complicated11Pass", myUser.getPassword());
        Assert.assertFalse(myUser.equals(myFriend));
        Assert.assertTrue(myUser.equals(new User("Vladislav", "very11Complicated11Pass")));
    }

    @Test
    public void tokenTest() {
        Token token1 = new Token("1234567890");
        Assert.assertEquals(1_234_567_890L, testToken.getToken());
        Assert.assertTrue(token1.equals(testToken));

        token1.changeValue();
        Assert.assertFalse(token1.equals(testToken));
    }

    @Test
    public void tokenRecievingTest() throws Exception {
        TokenStorage.logined.clear();
        List<User> test = new ArrayList<>();
        List<Token> tokens = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            test.add(new User("user" + i, "passUser" + i));
        }
        for (User temp : test) {
            tokens.add(TokenStorage.getToken(temp));
        }
        for (Token temp : tokens) {
            Assert.assertTrue(TokenStorage.hasToken(temp));
        }
        Assert.assertEquals(test.size(), TokenStorage.getOnlineUsers().size());
        for (Token temp : tokens) {
            TokenStorage.removeToken(temp);
        }
        Assert.assertEquals(0, TokenStorage.getOnlineUsers().size());
    }

    @Test
    public void tokenStorageTest() throws Exception {
        Token myToken = TokenStorage.getToken(myUser);
        Token friendToken = TokenStorage.getToken(myFriend);

        Assert.assertTrue(TokenStorage.hasToken(myToken));
        Assert.assertTrue(TokenStorage.hasToken(friendToken));

        Assert.assertEquals(myUser, TokenStorage.findByToken(myToken));
        Assert.assertEquals(myFriend, TokenStorage.findByToken(friendToken));

        Assert.assertEquals(2, TokenStorage.getOnlineUsers().size());
        TokenStorage.removeToken(friendToken);
        Assert.assertFalse(TokenStorage.hasToken(friendToken));
        Assert.assertEquals(1, TokenStorage.getOnlineUsers().size());
    }

    @Test
    public void findByToken() throws Exception {
        Token myToken = TokenStorage.getToken(myUser);
        Assert.assertEquals(myUser.getUserName(), TokenStorage.findByToken(myToken).getUserName());
    }

    @After
    public void clear() {
        TokenStorage.logined.clear();
    }
}
