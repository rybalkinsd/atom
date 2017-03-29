package ru.atom.authserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by gammaker on 29.03.2017.
 */
public class TokenStoreTest {
    @Test public void testTokenStore() {
        assertTrue(TokenStore.getAllLoginedUsers().isEmpty());

        User user = new User("myname", "mypassword");
        Token mytoken = TokenStore.getTokenForUser(user);
        assertTrue(TokenStore.isTokenValid(mytoken));
        assertTrue(user.isLogined());

        Token mytoken2 = TokenStore.getTokenForUser(user);
        assertEquals(mytoken, mytoken2);

        assertEquals(user, TokenStore.getUserByToken(mytoken));

        assertEquals(1, TokenStore.getAllLoginedUsers().size());

        TokenStore.removeToken(mytoken);
        assertTrue(!TokenStore.isTokenValid(mytoken));
        assertTrue(TokenStore.getAllLoginedUsers().isEmpty());
    }
}
