package bomber.gameservice.controller;


import bomber.connectionhandler.EventHandler;
import bomber.connectionhandler.json.Json;
import bomber.games.gameobject.Bomb;
import bomber.games.gameobject.Explosion;
import bomber.games.gameobject.Player;
import bomber.games.gamesession.GameSession;
import bomber.games.model.Tickable;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;

import static bomber.gameservice.controller.GameController.gameSessionMap;

public class GameThread implements Runnable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GameThread.class);
    private final long gameId;
    private static final int FPS = 60;
    private static final long FRAME_TIME = 1000 / FPS;
    private final Set<Tickable> tickables = new ConcurrentSkipListSet<>();
    private final AtomicLong tickNumber = new AtomicLong(0);
    private GameSession gameSession;

    public GameThread(final long gameId) {
        this.gameId = gameId;
    }

    @Override
    public void run() {
        log.info("Start new thread called game-mechanics with gameId = " + gameId);
        synchronized (this) {
            gameSession = new GameSession((int) gameId, tickables);
            log.info("Game has been init gameId={}", gameId);
            gameSession.setupGameMap();
            gameSessionMap.put(gameId, gameSession);
        }
        while (!Thread.currentThread().isInterrupted() || !gameSession.isGameOver()) {
            if (gameSession.isGameOver())
                Thread.currentThread().interrupt();
            long started = System.currentTimeMillis();
            act(FRAME_TIME);
            long elapsed = System.currentTimeMillis() - started;
            if (elapsed < FRAME_TIME) {
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(FRAME_TIME - elapsed));
            } else {
                log.warn("tick lag {} ms", elapsed - FRAME_TIME);
            }

            tickNumber.incrementAndGet();

        }

    }

    private void act(long elapsed) {
        try {
            EventHandler.sendReplica(gameSession.getId());
        } catch (IOException e) {
            log.error("Error to send REPLICA");
        }
        int gameOverCondition = GameSession.MAX_PLAYER_IN_GAME;
        for (Tickable tickable : tickables) {
            if (tickable instanceof Player)
                gameOverCondition--;
            tickable.tick(elapsed);
            if (tickable instanceof Bomb || tickable instanceof Explosion) {
                if (!tickable.isAlive()) {
                    if (tickable instanceof Bomb) {
                        Player tmpPlayer = (Player) gameSession.getReplica().get(((Bomb) tickable).getPlayerId());
                        tmpPlayer.decBombCount();
                    }
                    log.info("it IS'NT alive");
                    unregisterTickable(tickable);
                }
            }
        }




        if (gameOverCondition == (GameSession.MAX_PLAYER_IN_GAME - 1)) {
            log.info("------------------");
            log.info("GAME OVER");
            log.info("------------------");
            gameSession.setGameOver(true);
        } else {
            log.info("В живых осталось {}", GameSession.MAX_PLAYER_IN_GAME - gameOverCondition);
            if (!gameSession.getInputQueue().isEmpty()) {
                gameSession.getGameMechanics().readInputQueue(gameSession.getInputQueue());
                gameSession.getGameMechanics().doMechanic(gameSession.getReplica(), gameSession.getIdGenerator(),
                        tickables);
                gameSession.getGameMechanics().clearInputQueue(gameSession.getInputQueue());
                log.info("========================================");
                log.info(Json.replicaToJson(gameSession.getReplica(), gameSession.isGameOver()));
            } else {
                gameSession.getGameMechanics().doMechanic(gameSession.getReplica(), gameSession.getIdGenerator(),
                        tickables);

            }
        }


    }

    public AtomicLong getTickNumber() {
        return tickNumber;
    }

    public void unregisterTickable(Tickable tickable) {
        tickables.remove(tickable);
    }

}