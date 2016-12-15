package server;

import org.junit.Before;
import org.junit.Test;
import test_client.RestClient;
import test_client.RestClientImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegisterTest {

    private RestClient client = new RestClientImpl();

    private final String ALREADY_REGISTERED_USERNAME = "tester";
    private final String ALREADY_REGISTERED_PASSWORD = "tester";
    private final String NOT_REGISTERED_USERNAME = "test";
    private final String NOT_REGISTERED_PASSWORD = "test";

    @Before
    public void registerDefaultValue() {
        client.register(ALREADY_REGISTERED_USERNAME, ALREADY_REGISTERED_PASSWORD);
    }

    @Test
    public void returnTrueThenRegisteringWithNotExistingData() {
        assertTrue(client.register(NOT_REGISTERED_USERNAME, NOT_REGISTERED_PASSWORD));
    }

    @Test
    public void returnFalseThenRegisteringWithExistingData() {
        assertFalse(client.register(ALREADY_REGISTERED_USERNAME, ALREADY_REGISTERED_PASSWORD));
    }

}
