package gs.connection;

import gs.controller.MatchmakerController;
import gs.network.MatchmakerClient;
import gs.service.MatchmakerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by sergey on 3/14/17.
 */
@Component
public class MatchMaker extends Thread {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MatchMaker.class);
    private static final int PLAYERS_IN_GAME = 2;
    private long gameId = -1;

    @Autowired
    MatchmakerClient client;

    @Autowired
    MatchmakerService service;

    @Override
    public void run() {
        gameId = Long.parseLong(client.createPost(PLAYERS_IN_GAME));
        logger.info("Game created id={}", gameId);
        List<String> candidates = new ArrayList<>(PLAYERS_IN_GAME);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                candidates.add(
                        ConnectionQueue.getInstance().poll(10_000, TimeUnit.SECONDS)
                );
            } catch (InterruptedException e) {
                logger.warn("Timeout reached");
            }
            if (candidates.size() == PLAYERS_IN_GAME) {
                putJoins(candidates, gameId);
                //client.start();
                //logger.info("game started id={}", gameId);
                candidates.clear();
                gameId = Long.parseLong(client.createPost(PLAYERS_IN_GAME));
            }
        }
    }

    private void putJoins(List<String> players, long gameId) {
        for(String i : players) {
            ThreadSafeStorage.getInstance().put(i, gameId);
        }
    }
}
