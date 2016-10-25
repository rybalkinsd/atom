package server;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.model.user.User;
import test_client.RestClient;
import test_client.RestClientImpl;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AuthenticationTest {

    private static final RestClient CLIENT = new RestClientImpl();
    private static final String ALREADY_REGISTERED_USERNAME = "admin";
    private static final String ALREADY_REGISTERED_PASSWORD = "admin";
    private static final String ANOTHER_ALREADY_REGISTERED_USERNAME = "tester";
    private static final String ANOTHER_ALREADY_REGISTERED_PASSWORD = "tester";
    private static final String NOT_REGISTERED_USERNAME = "test";
    private static final String NOT_REGISTERED_PASSWORD = "test";
    private static final Long WRONG_TOKEN = new Random().nextLong();

    @Before
    public void start() throws Exception {
        CLIENT.register(ALREADY_REGISTERED_USERNAME, ALREADY_REGISTERED_PASSWORD);
        CLIENT.register(ANOTHER_ALREADY_REGISTERED_USERNAME, ANOTHER_ALREADY_REGISTERED_PASSWORD);
    }

    @Test
    public void loginMustGenerateNotNullToken() throws Exception {
        Long rightToken =
                CLIENT.login(ALREADY_REGISTERED_USERNAME, ALREADY_REGISTERED_PASSWORD);
        assertNotNull(rightToken);
    }

    @Test
    public void loginMustGenerateDifferentTokensForDifferentUsers() {
        Long rightToken =
                CLIENT.login(ALREADY_REGISTERED_USERNAME, ALREADY_REGISTERED_PASSWORD);
        Long differentRightToken =
                CLIENT.login(ANOTHER_ALREADY_REGISTERED_USERNAME, ANOTHER_ALREADY_REGISTERED_PASSWORD);
        assertNotEquals(rightToken, differentRightToken);
    }

    @Test
    public void loginMustGenerateSameTokenForSameUser() {
        Long rightToken =
                CLIENT.login(ALREADY_REGISTERED_USERNAME, ALREADY_REGISTERED_PASSWORD);
        Long sameRightToken =
                CLIENT.login(ALREADY_REGISTERED_USERNAME, ALREADY_REGISTERED_PASSWORD);
        assertEquals(rightToken, sameRightToken);
    }

    @Test
    public void loginMustReturnNullTokenForWrongLoginOrPassword() {
        Long wrongToken = CLIENT.login(NOT_REGISTERED_USERNAME, NOT_REGISTERED_PASSWORD);
        assertNull(wrongToken);
    }

    @Test
    public void getUsersMustReturnNotNullListOfLogInUsers() {
        List<User> result = CLIENT.getUsers();
        System.out.println(result);
        assertNotNull(result);
    }

    @Test
    public void getUsersMustReturnEmptyListIfNoLogInUser() {
        Long rightToken = CLIENT.login(ALREADY_REGISTERED_USERNAME, ALREADY_REGISTERED_PASSWORD);
        CLIENT.logout(rightToken);
        List<User> result = CLIENT.getUsers();
        System.out.println(result);
        assertEquals(0, result.size());
    }

    @Test
    public void changeNameMustReturnTrueWhenChangingNameToNotRegistered() {
        Long rightToken = CLIENT.login(ALREADY_REGISTERED_USERNAME, ALREADY_REGISTERED_PASSWORD);
        boolean result = CLIENT.changeName(rightToken, NOT_REGISTERED_USERNAME);
        assertTrue(result);
    }

    @Test
    public void changeNameMustReturnFalseWhenChangingNameToAlreadyRegistered() {
        Long rightToken = CLIENT.login(ALREADY_REGISTERED_USERNAME, ALREADY_REGISTERED_PASSWORD);
        boolean result = CLIENT.changeName(rightToken, ALREADY_REGISTERED_USERNAME);
        assertFalse(result);
    }

    @Test
    public void changeNameMustReturnFalseWhenChangingNameWithWrongToken() {
        boolean result = CLIENT.changeName(WRONG_TOKEN, NOT_REGISTERED_USERNAME);
        assertFalse(result);
    }

    @Test
    public void logoutMustReturnTrueWithRightToken() {
        Long rightToken = CLIENT.login(ALREADY_REGISTERED_USERNAME, ALREADY_REGISTERED_PASSWORD);
        boolean result = CLIENT.logout(rightToken);
        assertTrue(result);
    }

    @Test
    public void logoutMustReturnFalseWithWrongToken() {
        assertFalse(CLIENT.logout(WRONG_TOKEN));
    }

}
