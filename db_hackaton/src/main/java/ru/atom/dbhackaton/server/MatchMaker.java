package ru.atom.dbhackaton.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.dbhackaton.server.model.GameSession;
import ru.atom.dbhackaton.server.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by pavel on 17.04.17.
 */
public class MatchMaker implements Runnable {
    private static final Logger log = LogManager.getLogger(MatchMaker.class);

    @Override
    public void run() {
        log.info("MatchMaker started!");
        List<User> usersInMatch = new ArrayList<>(GameSession.PLAYERS_IN_GAME);

        while (!Thread.currentThread().isInterrupted()) {
            try {
                usersInMatch.add(
                        ThreadSafeQueue.getInstance()
                        .poll(10_000, TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                log.warn("TimeOut reached!");
            }

            if (usersInMatch.size() == GameSession.PLAYERS_IN_GAME) {
                GameSession gameSession = new GameSession();
                gameSession.setPlayers(usersInMatch);
                log.info("Game started!");
                //TODO add in database
                usersInMatch.clear();
            }
        }

    }
}
