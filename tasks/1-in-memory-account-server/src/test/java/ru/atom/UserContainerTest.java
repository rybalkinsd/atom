package ru.atom;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UserContainerTest {

    User user1;
    User user2;

    @Before
    public void setUp() {
        user1 = new User("Vasya", "123");
        user2 = new User("Petya", "321");
    }

    @Test
    public void registerUser() throws Exception {
        UserContainer.registerUser(user1);
        UserContainer.registerUser(user2);

        assertTrue(UserContainer.getRegisteredUsers().contains(user1));
        assertTrue(UserContainer.getRegisteredUsers().contains(user2));
    }

    @Test
    public void loginUser() throws Exception {
        UserContainer.registerUser(user1);
        UserContainer.registerUser(user2);

        UserContainer.login(user1);
        UserContainer.login(user2);

        assertTrue(UserContainer.getLogginedUsers().contains(user1));
        assertTrue(UserContainer.getLogginedUsers().contains(user2));
    }

    @Test
    public void logoutUser() throws Exception {
        UserContainer.registerUser(user1);
        UserContainer.registerUser(user2);

        UserContainer.login(user1);
        UserContainer.login(user2);

        Long user1Tocken = UserContainer.getTockenByUser(user1);
        Long user2Tocken = UserContainer.getTockenByUser(user2);

        UserContainer.logout(user1Tocken);
        UserContainer.logout(user2Tocken);

        assertTrue(!UserContainer.getLogginedUsers().contains(user1));
        assertTrue(!UserContainer.getLogginedUsers().contains(user2));
    }
}
