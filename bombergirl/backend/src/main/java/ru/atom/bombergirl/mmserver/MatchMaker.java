package ru.atom.bombergirl.mmserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ikozin on 17.04.17.
 */
public class MatchMaker implements Runnable {
    private static final Logger log = LogManager.getLogger(MatchMakerServer.class);
    private static AtomicLong idGame = new AtomicLong();

    public static Long getIdGame() {
        return idGame.get();
    }

    @Override
    public void run() {
        log.info("MM Started");
        List<Connection> candidates = new ArrayList<>(GameSession.PLAYERS_IN_GAME);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                candidates.add(
                        ThreadSafeQueue.getInstance().poll(10_000, TimeUnit.SECONDS)
                );
                log.info("candidates size is " + candidates.size());
            } catch (InterruptedException e) {
                log.warn("Timeout reached");
            }

            if (candidates.size() == GameSession.PLAYERS_IN_GAME) {
                GameSession session = new GameSession(candidates.toArray(new Connection[0]));
                Thread gameSession = new Thread(session);
                gameSession.setName("gameSession " + idGame);
                gameSession.start();
                log.info(session);
                ThreadSafeStorage.put(session);
                candidates.clear();
                idGame.getAndIncrement();
                log.info("idGame : " + idGame);
            }
        }
    }
}
