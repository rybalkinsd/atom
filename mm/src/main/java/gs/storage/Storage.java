package gs.storage;

import gs.model.GameSession;

import java.util.concurrent.ConcurrentHashMap;

public class Storage {
    private static final Storage INSTANCE = new Storage();

    private ConcurrentHashMap<Long, GameSession> gameSessions;

    private Storage() {
        this.gameSessions = new ConcurrentHashMap<Long, GameSession>();
    }

    public Storage getInstance() {
        return INSTANCE;
    }
}
