package matchmaker.connection;

import matchmaker.network.MatchmakerClient;
import matchmaker.service.MatchmakerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class MatchMaker extends Thread {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MatchMaker.class);
    private static final int PLAYERS_IN_GAME = 2;
    private long gameId;

    @Autowired
    MatchmakerClient client;

    @Autowired
    MatchmakerService service;

    @Override
    public void run() {
        gameId = Long.parseLong(client.createPost(PLAYERS_IN_GAME));
        service.saveGameSession(gameId, PLAYERS_IN_GAME);
        logger.info("Game created id={}", gameId);
        List<String> candidates = new ArrayList<>(PLAYERS_IN_GAME);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String player = ConnectionQueue.getInstance().poll(10_000, TimeUnit.SECONDS);
                candidates.add(player);
                putJoin(player, gameId);
                service.saveUser(player, gameId);
            } catch (InterruptedException e) {
                logger.warn("Timeout reached");
            }
            if (candidates.size() == PLAYERS_IN_GAME) {
                candidates.clear();
                gameId = Long.parseLong(client.createPost(PLAYERS_IN_GAME));
                service.saveGameSession(gameId, PLAYERS_IN_GAME);
            }
        }
    }

    private void putJoin(String player, long gameId) {
        Connections.getInstance().put(player, gameId);
    }
}
