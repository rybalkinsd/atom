package gameservice;

import gameservice.model.GameObject;
import gameservice.model.GameSession;
import gameservice.model.Pawn;
import gameservice.gamemechanics.Action;
import gameservice.gamemechanics.GameMechanics;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Sessions {
    private static ConcurrentHashMap<GameSession, ArrayList<WebSocketSession>> storageSessions
            = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Long, GameSession> sessions = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Pawn, WebSocketSession> pawnToWebsocket
            = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<GameSession, GameMechanics> tickables = new ConcurrentHashMap<>();
    private static long lastId;

    public static GameSession getByWebsocket(WebSocketSession session) {
        for (Map.Entry<GameSession, ArrayList<WebSocketSession>> i : storageSessions.entrySet()) {
            for (WebSocketSession j : i.getValue()) {
                if (session.equals(j)) {
                    return i.getKey();
                }
            }
        }
        return null;
    }

    public static ArrayList<WebSocketSession> getWebsocketsByGameSession(GameSession session) {
        return storageSessions.get(session);
    }

    public static void addByGameId(long gameId, WebSocketSession session) {
        for (ConcurrentHashMap.Entry<GameSession, ArrayList<WebSocketSession>> entry : storageSessions.entrySet()) {
            if (entry.getKey().getId() == gameId) {
                entry.getValue().add(session);
            }
        }
    }

    public static long addSession(int playerCount) {
        GameSession gameSession = new GameSession(playerCount, ++lastId);
        storageSessions.put(gameSession, new ArrayList<>(playerCount));
        sessions.put(lastId, gameSession);
        return lastId;
    }

    public static int getId(long gameId) {
        return storageSessions.get(getSessionById(gameId)).size();
    }

    public static GameSession getSessionById(long gameId) {
        Optional<GameSession> first = storageSessions.keySet().stream().filter((gs) ->
                gameId == gs.getId()).findFirst();
        return first.get();
    }

    public static Pawn getPawnBySocket(WebSocketSession session) {
        for (Map.Entry<Pawn, WebSocketSession> i : pawnToWebsocket.entrySet()) {
            if (i.getValue().equals(session)) {
                return i.getKey();
            }
        }
        return null;
    }

    public static void putPawnToSocket(WebSocketSession session, GameObject object) {
        pawnToWebsocket.put((Pawn) object, session);
    }

    public static void putTickables(GameMechanics gameMechanics, GameSession session) {
        tickables.put(session, gameMechanics);
    }

    public static void putAction(GameSession session, Action action) {
        tickables.get(session).putAction(action);
    }
}
