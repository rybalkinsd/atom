package gs.storage;

import gs.model.GameObject;
import gs.model.GameSession;
import gs.model.Girl;
import gs.ticker.Action;
import gs.ticker.Ticker;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionStorage {
    private static ConcurrentHashMap<GameSession, ArrayList<WebSocketSession>> storage
            = new ConcurrentHashMap<GameSession, ArrayList<WebSocketSession>>();
    private static ConcurrentHashMap<Long, GameSession> sessions = new ConcurrentHashMap<Long, GameSession>();
    private static ConcurrentHashMap<Girl, WebSocketSession> girlToWebsocket
            = new ConcurrentHashMap<Girl, WebSocketSession>();
    private static ConcurrentHashMap<GameSession, Ticker> tickers = new ConcurrentHashMap<GameSession, Ticker>();
    private static long lastId = -1;

    public static boolean containsGameSession(long gameId) {
        for (ConcurrentHashMap.Entry<GameSession, ArrayList<WebSocketSession>> entry : storage.entrySet()) {
            if (entry.getKey().getId() == gameId) {
                return true;
            }
        }
        return false;
    }

    public static GameSession getByWebsocket(WebSocketSession session) {
        for (Map.Entry<GameSession, ArrayList<WebSocketSession>> i : storage.entrySet()) {
            for (WebSocketSession j : i.getValue()) {
                if (session.equals(j)) {
                    return i.getKey();
                }
            }
        }
        return null;
    }

    public static boolean isGameReady(long gameId) {
        return storage.get(sessions.get(gameId)).size() == sessions.get(gameId).getPlayerCount();
    }

    public static ArrayList<WebSocketSession> getWebsocketsByGameSession(GameSession session) {
        return storage.get(session);
    }

    public static void addByGameId(long gameId, WebSocketSession session) {
        for (ConcurrentHashMap.Entry<GameSession, ArrayList<WebSocketSession>> entry : storage.entrySet()) {
            if (entry.getKey().getId() == gameId) {
                entry.getValue().add(session);
            }
        }
    }

    public static long addSession(int playerCount) {
        GameSession gameSession = new GameSession(playerCount, ++lastId);
        storage.put(gameSession, new ArrayList<WebSocketSession>(playerCount));
        sessions.put(lastId, gameSession);
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

    public static int getId(long gameId) {
        return storage.get(getSessionById(gameId)).size();
    }

    public static GameSession getSessionById(long gameId) {
        Optional<GameSession> first = storage.keySet().stream().filter((gs) -> gameId == gs.getId()).findFirst();
        return first.get();
    }

    public static Girl getGirlBySocket(WebSocketSession session) {
        for (Map.Entry<Girl, WebSocketSession> i : girlToWebsocket.entrySet()) {
            if (i.getValue().equals(session)) {
                return i.getKey();
            }
        }
        return null;
    }

    public static void putGirlToSocket(WebSocketSession session, GameObject object) {
        girlToWebsocket.put((Girl) object, session);
    }

    public static void putTicker(Ticker ticker, GameSession session) {
        tickers.put(session, ticker);
    }

    public static void putAction(GameSession session, Action action) {
        tickers.get(session).putAction(action);
    }

    public static Ticker getTickerByGameSession(GameSession session) {
        return tickers.get(session);
    }
}
