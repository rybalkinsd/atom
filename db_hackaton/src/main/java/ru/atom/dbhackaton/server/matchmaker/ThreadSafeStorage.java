package ru.atom.dbhackaton.server.matchmaker;

import ru.atom.dbhackaton.server.model.GameSession;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by pavel on 17.04.17.
 */
public class ThreadSafeStorage {
    private static ConcurrentHashMap<Long, GameSession> map = new ConcurrentHashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong();

    public static void put(GameSession session) {
        map.put(idGenerator.getAndIncrement(), session);
    }

    public static GameSession getSessionById(Long id) {
        return map.get(id);
    }

    public static Long getCurrentGameId() {
        return idGenerator.get();
    }
}
