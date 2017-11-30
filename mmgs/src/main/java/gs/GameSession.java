package gs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sergey on 3/14/17.
 */
public class GameSession {
    private static final Logger log = LogManager.getLogger(GameSession.class);
    private static AtomicLong idGenerator = new AtomicLong();

    public static final int PLAYERS_IN_GAME = 2;

    private final int playerCount;
    private final long id = idGenerator.getAndIncrement();

    public GameSession(int playerCount) {
        this.playerCount = playerCount;
    }

    public long getId() {
        return id;
    }
}
