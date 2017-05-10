package ru.atom.websocket.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.Nullable;
import ru.atom.Ticker;
import ru.atom.geometry.Point;
import ru.atom.websocket.model.Movable;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by BBPax on 04.05.17.
 */
public class GameManager {
    private static final Logger log = LogManager.getLogger(GameManager.class);
    private static final GameManager instance = new GameManager();
    private static final int PARALLELISM_LEVEL = 4;  // TODO: 06.05.17   MAX_PLAYERS

    private final ConcurrentLinkedQueue<Ticker> games;
    private Ticker currentGame;
    private static final Object gameLock = new Object();

    private GameManager() {
        games = new ConcurrentLinkedQueue<>();
        currentGame = new Ticker();
    }

    public static GameManager getInstance() {
        return instance;
    }

    public void addPlayer(Session session, String login) {
//        log.info("number of players in currentGame before add: {}", currentGame.numberOfPlayers());
        currentGame.addPawn(session, login);
//        log.info("number of players in currentGame after add: {}", currentGame.numberOfPlayers());
        if (currentGame.numberOfPlayers() == PARALLELISM_LEVEL) {
            startGame();
            games.offer(currentGame);
            log.info("game is started");
            currentGame = new Ticker();
            log.info("next game is ready to add new players");
        }
    }

    public void plantBomb(Session session) {
        Ticker ticker = findBySession(session);
        if (ticker != null) {
//            log.info("plant bomb after game is ready to start");
            ticker.plantBomb(session);
        } else {
            log.info("ignore plant bomb before game is ready to start");
        }
    }

    public void move(Session session, Movable.Direction direction) {
        Ticker ticker = findBySession(session);
        if (ticker != null) {
//            log.info("move after game is ready to start");
            ticker.move(session, direction);
        } else {
            log.info("ignore move before game is ready to start");
        }
    }

    public void removePlayer(Session session) {
        Ticker ticker = findBySession(session);
        if(ticker == null) {
//            log.info("number of players in currentGame before remove: {}", currentGame.numberOfPlayers());
            log.info("{} pawn was killed", currentGame.killPawn(session));
//            log.info("number of players in currentGame after remove: {}", currentGame.numberOfPlayers());
        } else {
            log.info("{} pawn will be killed in game", ticker.killPawn(session));
        }
        if(ticker.numberOfPlayers() == 0) {
            ticker.interrupt();
//            log.info("games size before interrupting: {}", games.size());
            try {
                ticker.join();
            } catch (InterruptedException e) {
                log.info("some bitches falled me down");
            }
            games.remove(ticker);
//            log.info("games size after interrupting: {}",games.size());
        }
    }

    private void startGame() {
        currentGame.start();
    }

    @Nullable private Ticker findBySession(Session session) {
        try {
            return games.stream().filter(t -> t.containSession(session))
                    .findAny().get();
        } catch (NoSuchElementException e) {
            log.info("Player with such session is not playing now");
            return null;
        }
    }
}
