package ru.atom.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.tick.Ticker;

import java.util.concurrent.ConcurrentHashMap;

public class GameServerService {
    private static final Logger log = LogManager.getLogger(GameServerService.class);
    private static ConcurrentHashMap<Long, GameSession> gameSessionMap = new ConcurrentHashMap<>();

    public static long createGameSession(short playerAmount) {
        GameSession gameSession = new GameSession(playerAmount);
        log.info("Game session with " + playerAmount + "players created, returned id - " + gameSession.getId());
        gameSessionMap.put(gameSession.getId(), gameSession);
        return gameSession.getId();
    }

    public static GameSession getGameSession(long gameId) {
        return gameSessionMap.get(gameId);
    }

}
