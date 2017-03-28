package ru.atom.rk01;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dmbragin on 3/28/17.
 */
public class UserManagerTest {
    private UserManager userManager;
    private User testUser;
    private User badTestUser;
    @Before
    public void setUp() throws Exception {
        userManager = new UserManager();
        testUser = new User("test", "test");
        badTestUser = new User("test", "test1");
        userManager.register(testUser);
    }

    @Test
    public void getAll() throws Exception {
        assertEquals(userManager.getAll().size(), 1);
    }

    @Test
    public void register() throws Exception {
        boolean result = userManager.register(testUser);
        assertFalse(result);
        boolean result1 = userManager.register(new User("test1", "test1"));
        assertTrue(result1);
        assertEquals(userManager.getAll().size(), 2);
        boolean result2 = userManager.register(badTestUser);
        assertFalse(result2);
        assertEquals(userManager.getAll().size(), 2);
    }

    @Test
    public void login() throws Exception {
        Token token1 = userManager.login(testUser);
        Token token2 = userManager.login(testUser);
        assertEquals(token1, token2);
        Token token3 = userManager.login(badTestUser);
        assertEquals(token3, null);

    }

    @Test
    public void logout() throws Exception {
        Token token1 = userManager.login(testUser);
        assertTrue(userManager.logout(testUser));
        assertFalse(userManager.logout(testUser));

    }

    @Test
    public void getLogined() throws Exception {
        assertEquals(userManager.getLogined().size(), 0);
        Token token1 = userManager.login(testUser);
        assertEquals(userManager.getLogined().size(), 1);
    }

    @Test
    public void getUserByToken() throws Exception {
        Token token1 = userManager.login(testUser);
        User user = userManager.getUserByToken(token1);
        assertEquals(user, testUser);
    }

}