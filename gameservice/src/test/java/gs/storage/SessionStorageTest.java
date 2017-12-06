package gs.storage;

import gs.GameServer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SessionStorageTest {

    @Before
    public void startServer() {
        GameServer.startGs();
    }

    @Test
    public void storageTest() {
        assertFalse(SessionStorage.containsGameSession(1));
        for (int i = 0; i < 5; i++) SessionStorage.addSession(4);
        assertTrue(SessionStorage.containsGameSession(3));
        assertFalse(SessionStorage.containsGameSession(6));
    }
}
