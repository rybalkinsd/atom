package ru.atom.gameserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atom.gameserver.component.ConnectionHandler;
import ru.atom.gameserver.gsession.GameSession;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameRepository {

    private final Map<Long, GameSession> games = new ConcurrentHashMap<>();

    @Autowired
    private ConnectionHandler connectionHandler;

    public Long createGame(int playersNum) {
        Long gameId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        games.put(gameId, new GameSession(gameId, connectionHandler));
        return gameId;
    }

    public GameSession getGameById(Long id) {
        return games.get(id);
    }

}
