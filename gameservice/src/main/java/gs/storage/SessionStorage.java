package gs.storage;

import gs.model.GameSession;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStorage {
    private static ConcurrentHashMap<GameSession, ArrayList<WebSocketSession>> storage
            = new ConcurrentHashMap<GameSession, ArrayList<WebSocketSession>>();
    private static long lastId = -1;

    public static long addSession(int playerCount) {
        storage.put(new GameSession(playerCount, ++lastId), new ArrayList<WebSocketSession>());
        return lastId;
    }
}
