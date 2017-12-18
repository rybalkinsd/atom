package gs.storage;

import gs.model.GameObject;
import gs.model.GameSession;
import gs.model.Girl;
import gs.network.Broker;
import gs.ticker.Action;
import gs.ticker.Ticker;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static gs.message.Topic.GAME_OVER;

@Component
public class SessionStorage {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SessionStorage.class);
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

    public static boolean isGameReady(GameSession session) {
        return session.isReady();
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

    public static WebSocketSession getWebsocketByGirl(Girl girl) {
        /*for (Map.Entry e : girlToWebsocket.entrySet()) {
            if (e.getKey().equals(girl)) {
                return (WebSocketSession) e.getValue();
            }
        }*/
        return girlToWebsocket.get(girl);
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

    public static void removeWebsocket(WebSocketSession session) {
        for (Map.Entry e : storage.entrySet()) {
            ArrayList<WebSocketSession> tmp = (ArrayList<WebSocketSession>) e.getValue();
            if (tmp.contains(session)) {
                GameSession gameSession = (GameSession) e.getKey();
                gameSession.removeGameObject(getGirlBySocket(session));
                tmp.remove(session);
                if (tmp.size() == 1) {
                    Broker.getInstance().send(tmp.get(0), GAME_OVER, "YOU WIN!");
                    removeGameSession(gameSession);
                }
                if (tmp.isEmpty()) {
                    Broker.getInstance().send(tmp.get(0), GAME_OVER, "YOU WIN!");
                    removeGameSession(gameSession);
                }
            }
        }
        girlToWebsocket.remove(getGirlBySocket(session));
        log.info("Websocket session: " + session + "removed");
    }

    public static void removeGameSession(GameSession session) {
        storage.remove(session);
        for (Map.Entry e : sessions.entrySet()) {
            if (e.getValue().equals(session)) sessions.remove(e.getKey());
        }
        tickers.get(session).kill();
        tickers.remove(session);
        log.info("Session " + session.getId() + " removed");
    }
}
