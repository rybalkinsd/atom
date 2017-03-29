package ru.atom;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import ru.atom.model.Token;
import ru.atom.model.TokenStorage;
import ru.atom.model.User;
import ru.atom.server.AuthResources;
import ru.atom.server.DataResources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dmitriy on 28.03.17.
 */
public class DataResourcesTest {
    private static final Logger log = LogManager.getLogger(DataResourcesTest.class);
    static AtomicInteger atomInt = new AtomicInteger(100);
    static String typicalName = "username";
    static String typicalPassword = "qwerty12345";

    @Test
    public void displayAll() throws Exception {
        Token token;
        User user;
        for (int i = 0; i < 10; i++) {
            user = new User(typicalName + atomInt.getAndIncrement(), typicalPassword);
            token = new Token(user);
            TokenStorage.insert(token, user);
        }
        Response response = DataResources.displayAll();
        log.info("Object created");
        Assert.assertTrue(response.getStatus() == 200);
    }
}
