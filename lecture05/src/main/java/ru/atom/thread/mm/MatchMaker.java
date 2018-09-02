package ru.atom.thread.mm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by sergey on 3/14/17.
 */
public class MatchMaker implements Runnable {
    private static final Logger log = LogManager.getLogger(MatchMaker.class);


    @Override
    public void run() {
        log.info("Started");
        List<Connection> candidates = new ArrayList<>(GameSession.PLAYERS_IN_GAME);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                candidates.add(
                        ConnectionQueue.getInstance().poll(10_000, TimeUnit.SECONDS)
                );
            } catch (InterruptedException e) {
                log.warn("Timeout reached");
            }

            if (candidates.size() == GameSession.PLAYERS_IN_GAME) {
                GameSession session = new GameSession(candidates.toArray(new Connection[0]));
                log.info(session);
                GameRepository.put(session);
                candidates.clear();
            }
        }
    }
}
