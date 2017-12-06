package gs.storage;

import gs.model.GameSession;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionStorage {
    private static ConcurrentHashMap<GameSession, ArrayList<WebSocketSession>> storage
            = new ConcurrentHashMap<>();
    private static long lastId = -1;

    public static boolean containsGameSession(long gameId) {
        for (ConcurrentHashMap.Entry<GameSession, ArrayList<WebSocketSession>> entry : storage.entrySet()) {
            if (entry.getKey().getId() == gameId) {
                return true;
            }
        }
        return false;
    }

    public static void addByGameId(long gameId, WebSocketSession session) {
        for (ConcurrentHashMap.Entry<GameSession, ArrayList<WebSocketSession>> entry : storage.entrySet()) {
            if (entry.getKey().getId() == gameId) {
                entry.getValue().add(session);
            }
        }
    }

    public static long addSession(int playerCount) {
        storage.put(new GameSession(playerCount, ++lastId), new ArrayList<WebSocketSession>(playerCount));
        return lastId;
    }

    public static boolean removeSession(WebSocketSession session) {
        for (ConcurrentHashMap.Entry<GameSession, ArrayList<WebSocketSession>> entry : storage.entrySet()) {
            for (WebSocketSession i : entry.getValue()) {
                if (i.equals(session)) {
                    entry.getValue().remove(session);
                    return true;
                }
            }
        }
        return false;
    }
}
