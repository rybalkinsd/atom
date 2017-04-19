package ru.atom.mm;

import org.junit.Ignore;
import ru.atom.dbhackaton.resource.User;

import java.util.Random;

/**
 * Created by BBPax on 19.04.17.
 */
@Ignore
public class AuthTest {
    private User newUser = new User().setLogin("user" + new Random()
            .nextInt(999999))
            .setPassword("password");
}
