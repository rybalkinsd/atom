package ru.atom;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UserRkContainerTest {

    UserRk user1;
    UserRk user2;

    @Before
    public void setUp() {
        user1 = new UserRk("Vasya", "123");
        user2 = new UserRk("Petya", "321");
    }

    @Test
    public void registerUser() throws Exception {
        UserContainerRk.registerUser(user1);
        UserContainerRk.registerUser(user2);

        assertTrue(UserContainerRk.getRegisteredUsers().contains(user1));
        assertTrue(UserContainerRk.getRegisteredUsers().contains(user2));
    }

    @Test
    public void loginUser() throws Exception {
        UserContainerRk.registerUser(user1);
        UserContainerRk.registerUser(user2);

        UserContainerRk.login(user1);
        UserContainerRk.login(user2);

        assertTrue(UserContainerRk.getLogginedUsers().contains(user1));
        assertTrue(UserContainerRk.getLogginedUsers().contains(user2));
    }

    @Test
    public void logoutUser() throws Exception {
        UserContainerRk.registerUser(user1);
        UserContainerRk.registerUser(user2);

        UserContainerRk.login(user1);
        UserContainerRk.login(user2);

        Long user1Tocken = UserContainerRk.getTockenByUser(user1);
        Long user2Tocken = UserContainerRk.getTockenByUser(user2);

        UserContainerRk.logout(user1Tocken);
        UserContainerRk.logout(user2Tocken);

        assertTrue(!UserContainerRk.getLogginedUsers().contains(user1));
        assertTrue(!UserContainerRk.getLogginedUsers().contains(user2));
    }
}
