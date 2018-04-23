package gs;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sergey on 3/15/17.
 */
public class GameRepository {
    private static ConcurrentHashMap<Long, GameSession> map = new ConcurrentHashMap<>();

    public static long newSession(int playerCount) {
        GameSession gameSession = new GameSession(playerCount);
        map.put(gameSession.getiD(), gameSession);
        return gameSession.getiD();
    }

    public static Collection<GameSession> getAll() {
        return map.values();
    }
}
