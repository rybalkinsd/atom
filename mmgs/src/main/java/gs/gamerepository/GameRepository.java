package gs.gamerepository;

import gs.GameSession;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sergey on 3/15/17.
 */
public class GameRepository {

    private static ConcurrentHashMap<Long, GameSession> map = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Long, GameSession> getMap() {
        return map;
    }

    public static long newSession(int playerCount) {
        GameSession gameSession = new GameSession();
        map.put(gameSession.getiD(), gameSession);
        return gameSession.getiD();
    }

    public static Collection<GameSession> getAll() {
        return map.values();
    }
}
