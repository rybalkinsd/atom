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

    public Long createGame(int playersCnt) {
        Long gameId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        games.put(gameId, new GameSession(gameId, playersCnt, connectionHandler));
        return gameId;
    }

    public void deleteGame(Long id) {
        games.remove(id);
    }

    public GameSession getGameById(Long id) {
        return games.get(id);
    }

}
