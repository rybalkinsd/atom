package ru.atom.model;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class ModelTests {
    @Test
    public void authUser() throws Exception {
        TokenMap tokenMap = new TokenMap();
        UserMap userMap = new UserMap();

        User user1 = new User("Sergey");
        User user2 = new User("Roman");
        userMap.putUser(user1, "1234");
        assertThat(userMap.authenticate(user1, "1234"), is(true));
        assertThat(userMap.authenticate(user2, "1234"), is(false));
    }

    @Test
    public void validatePassword() throws Exception {
        TokenMap tokenMap = new TokenMap();
        UserMap userMap = new UserMap();

        User user1 = new User("Sergey");
        User user2 = new User("Roman");
        userMap.putUser(user1, "1234");
        userMap.putUser(user2, "5678");
        assertThat(userMap.authenticate(user1, "1234"), is(true));
        assertThat(userMap.authenticate(user2, "5678"), is(true));
        assertThat(userMap.authenticate(user1, "5678"), is(false));
        assertThat(userMap.authenticate(user2, "1234"), is(false));
    }
}