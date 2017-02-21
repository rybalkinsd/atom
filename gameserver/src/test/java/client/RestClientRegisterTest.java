package client;


import org.junit.BeforeClass;
import org.junit.Test;
import server.api.ApiServlet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RestClientRegisterTest {
    private static IRestClient client = new RestClient();

    @BeforeClass
    public static void registerDefaultValue() {
        String user = "tester";
        String password = "tester";
        client.register(user, password);
    }

    @Test
    public void registerNewValues() {
        String user = "testerock";
        String password = "test";
        assertTrue(client.register(user, password));
    }

    @Test
    public void registerExistedValues() {
        String user = "tester";
        String password = "test";
        assertFalse(client.register(user, password));
    }
}
