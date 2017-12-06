package gs;

import gs.model.Bonus;
import gs.model.Wall;
import gs.model.Bomb;
import gs.model.Fire;
import gs.model.Pawn;
import gs.model.Movable;
import gs.tick.Tickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.WebSocketSession;
import gs.geometry.Point;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class GameSession {
    private static final Logger log = LogManager.getLogger(GameSession.class);

    private final ConcurrentHashMap<String, Pawn> allPawns = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Point, Wall> allWalls = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Point, Bomb> allBombs = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Point, Fire> allFire = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Point, Bonus> allBonuses = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, WebSocketSession> allSessions = new ConcurrentHashMap<>();

    private static AtomicLong idGenerator = new AtomicLong();
    private int playersInGame = 0;

    public int getMaxPlayersInGame() {
        return maxPlayersInGame;
    }

    private int maxPlayersInGame = 0;
    private boolean gameSessionIsOver = false;

    private final long iD = UUID.randomUUID().getLeastSignificantBits();

    void increasePlayersInGame(String name) {
        maxPlayersInGame++;
        playersInGame++;
        if (playersInGame == 1) {
            allPawns.put(name, new Pawn(32, 32));
        } else if (playersInGame == 2) {
            allPawns.put(name, new Pawn(480, 32));
        } else if (playersInGame == 3) {
            allPawns.put(name, new Pawn(32, 352));
        } else if (playersInGame == 4) {
            allPawns.put(name, new Pawn(480, 352));
        } else {
            log.error("Wrong number of players!");
        }
    }

    void decreasePlayersInGame() {
        playersInGame--;
    }

    int getPlayersInGame() {
        return playersInGame;
    }

    void addSession(WebSocketSession session, String player) {
        allSessions.put(player, session);
    }

    public String jsonStringPawns() {
        if (allPawns.size() == 0) {
            return null;
        } else {
            String objjson = "";
            for (String name : allPawns.keySet()) {
                objjson = objjson + allPawns.get(name).toJson() + ",";
            }
            return objjson.substring(0, (objjson.length() - 1));
        }
    }

    public String jsonStringWalls() {
        String objjson = "";
        for (Point p : allWalls.keySet()) {
            Wall obj = allWalls.get(p);
            objjson = objjson + obj.toJson() + ",";
        }
        return objjson.substring(0, (objjson.length() - 1));
    }

    public String jsonStringBombs() {
        if (allBombs.size() == 0) {
            return null;
        } else {
            String objjson = "";
            for (Point p : allBombs.keySet()) {
                objjson = objjson + allBombs.get(p).toJson() + ",";
            }
            return objjson.substring(0, (objjson.length() - 1));
        }
    }

    public String jsonStringBonuses() {
        if (allBonuses.size() == 0) {
            return null;
        } else {
            String objjson = "";
            for (Point p : allBonuses.keySet()) {
                objjson = objjson + allBonuses.get(p).toJson() + ",";
            }
            return objjson.substring(0, (objjson.length() - 1));
        }
    }

    public String jsonStringExplosions() {
        if (allFire.size() == 0) {
            return null;
        } else {
            String objjson = "";
            for (Point p : allFire.keySet()) {
                objjson = objjson + allFire.get(p).toJson() + ",";
            }
            return objjson.substring(0, (objjson.length() - 1));
        }
    }

    public long getiD() {
        return iD;
    }

    void kilGameSession() {
        this.gameSessionIsOver = true;
    }

    public boolean isGameSessionIsOver() {
        return gameSessionIsOver;
    }

    public WebSocketSession getSession(String name) {
        return allSessions.get(name);
    }

    public ConcurrentHashMap<String, WebSocketSession> getAllSessions() {
        return allSessions;
    }

    public ConcurrentHashMap<String, Pawn> getAllPawns() {
        return allPawns;
    }

    ConcurrentHashMap<Point, Bomb> getAllBombs() {
        return allBombs;
    }

    ConcurrentHashMap<Point, Fire> getAllFire() {
        return allFire;
    }

    ConcurrentHashMap<Point, Wall> getAllWalls() {
        return allWalls;
    }

    ConcurrentHashMap<Point, Bonus> getAllBonuses() {
        return allBonuses;
    }

}
