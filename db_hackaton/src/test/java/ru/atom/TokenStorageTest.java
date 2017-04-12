package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import ru.atom.model.Token;
import ru.atom.model.TokenStorage;
import ru.atom.model.User;
import ru.atom.server.AuthResources;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dmitriy on 27.03.17.
 */
public class TokenStorageTest {
    private static final Logger log = LogManager.getLogger(TokenStorageTest.class);
    static AtomicInteger atomInt = new AtomicInteger(1000);
    static String typicalName = "username";
    static String typicalPassword = "qwerty12345";
    User user;

    @Test
    public void insert() {
        TokenStorage.getInstance().clear();
        Assert.assertTrue(TokenStorage.getInstance().size() == 0);
        Token token;
        User user;
        for (int i = 0; i < 5; i++) {
            user = new User(typicalName + atomInt.getAndIncrement(), typicalPassword);
            token = new Token(user);
            TokenStorage.insert(token, user);
            log.info("User {} added", user);
            Assert.assertTrue(TokenStorage.getInstance().size() == i + 1);
        }
    }

    @Test
    public void getByToken() {
        User user = new User(typicalName + atomInt.getAndIncrement(), typicalPassword);
        Token token = new Token(user);
        TokenStorage.insert(token, user);
        log.info("User {} added", user);
        Assert.assertTrue(TokenStorage.getByToken(token).equals(user));
        log.info("User {} with token = '{}' received", user, token);

        User user1 = new User(typicalName + atomInt.getAndIncrement(), typicalPassword);
        Assert.assertTrue(!TokenStorage.getByToken(token).equals(user1));
    }

    @Test
    public void validate() {
        User user = new User(typicalName + atomInt.getAndIncrement(), typicalPassword);
        AuthResources.register(user.getName(), typicalPassword);
        AuthResources.login(user.getName(), typicalPassword);
        Token token = new Token(user);
        Assert.assertTrue(TokenStorage.validate(token, user));

        User user1 = new User(typicalName + atomInt.getAndIncrement(),
                typicalPassword + "1");
        Token token1 = new Token(user1);
        Assert.assertTrue(!TokenStorage.validate(token1, user));

        User user2 = new User(typicalName + atomInt.getAndIncrement(), typicalPassword);
        Assert.assertTrue(!TokenStorage.validate(token, user2));
    }
}
