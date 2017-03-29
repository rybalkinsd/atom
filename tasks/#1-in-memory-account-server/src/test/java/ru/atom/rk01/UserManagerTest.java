package ru.atom.rk01;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by dmbragin on 3/28/17.
 */
public class UserManagerTest {
    private UserManager userManager;
    private User testUser;
    private User badTestUser;

    @Before
    public void setUp() throws Exception {
        UserManager.clear();
        userManager = UserManager.getInstance();
        testUser = new User("test", "test");
        badTestUser = new User("test", "test1");
        userManager.register(testUser);
    }

    @After
    public void tearDown() throws Exception {
        UserManager.clear();
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
        assertTrue(userManager.logout(token1));
        assertFalse(userManager.logout(token1));

    }

    @Test
    public void getLoginedUsers() throws Exception {
        int size = userManager.getLoginedUsers().size();
        Token token1 = userManager.login(new User("Lolita", "LooL"));
        assertEquals(userManager.getLoginedUsers().size(), size);
    }

    @Test
    public void getUserByToken() throws Exception {
        Token token1 = userManager.login(testUser);
        User user = userManager.getUserByToken(token1);
        assertEquals(user, testUser);
    }

}