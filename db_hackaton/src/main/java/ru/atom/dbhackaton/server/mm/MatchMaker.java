package ru.atom.dbhackaton.server.mm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MatchMaker implements Runnable {
    private static final Logger logger = LogManager.getLogger(MatchMaker.class);

    @Override
    public void run() {
        logger.info("MM started");
        List<Connection> candidates = new ArrayList<>(GameSession.PLAYERS_IN_GAME);

        while (!Thread.currentThread().isInterrupted()) {

            Connection connection = ThreadSafeQueue.getInstance().poll();
            if (connection != null) {
                candidates.add(connection);
            }

            if (candidates.size() == GameSession.PLAYERS_IN_GAME) {
                GameSession session = new GameSession(candidates.toArray(new Connection[0]));
//                GameSession session = new GameSession((Connection[]) candidates.toArray());
//                logger.info(session);
                try {
                    ThreadSafeStorage.put(session);
//                    session.sendIdToConnections();
                    logger.info("create new session! {}", session);
                } catch (NullPointerException e) {
                    System.out.println();
                }
                candidates.clear();
            }
        }
    }


}
