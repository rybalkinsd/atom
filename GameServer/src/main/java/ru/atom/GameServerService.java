package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameServerService {
    private static final Logger log = LogManager.getLogger(GameServerService.class);

    private static ConcurrentHashMap<Long, GameSession> gameSessionMap = new ConcurrentHashMap<>();
    private static CopyOnWriteArrayList<Tickable> tickableObjects = new CopyOnWriteArrayList<>();

    public static void subscribeTickEvent(Tickable tickableObject) {
        tickableObjects.add(tickableObject);
        log.info("TICKABLE OBJECTS+: " + tickableObjects.size());
    }

    public static void unSubscribeTickEvent(Tickable tickableObject) {
        log.info("TICKABLE OBJECTS-: " + tickableObjects.size());
        tickableObjects.remove(tickableObject);
    }

    public static long createGameSession(short playerAmount) {
        log.info("Game session with " + playerAmount + "players - created");
        GameSession gameSession = new GameSession(playerAmount);
        gameSessionMap.put(gameSession.getId(), gameSession);
        return gameSession.getId();
    }

    public static GameSession getGameSession(long gameId) {
        return gameSessionMap.get(gameId);
    }

    public static void run() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("game server service started");
                while (!Thread.currentThread().isInterrupted()) {
                    tickableObjects
                            .forEach(tickable -> tickable.tick(1));
                   /* try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        });
        thread.start();
    }
}
