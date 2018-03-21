package ru.atom.mm.service;

import org.springframework.stereotype.Repository;
import ru.atom.mm.model.GameSession;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sergey on 3/15/17.
 */
@Repository
public class GameRepository {
    private ConcurrentHashMap<Long, GameSession> map = new ConcurrentHashMap<>();

    public void put(GameSession session) {
        map.put(session.getId(), session);
    }

    public Collection<GameSession> getAll() {
        return map.values();
    }
}
