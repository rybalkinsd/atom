package mm;


import mm.dao.Player;
import mm.dao.PlayerDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

@Service
public class Matchmaker implements Runnable {
    private static final Logger log = LogManager.getLogger(Matchmaker.class);

    @Autowired
    GameService client;
    private static final int PLAYER_COUNT = 4;
    private static final int TIMEOUT = 10;
    private long gameId = 100;
    private PlayerDao playerDao = new PlayerDao();
    private BlockingQueue<Player> queue = new LinkedBlockingQueue<>();
    Hashtable <String, Long> inGamePlayers = new Hashtable<>();

    public boolean join(@NotNull String name) {
        return queue.offer(new Player(0, name));
    }

    @Override
    public void run() {
        List<Player> players = new ArrayList<>(PLAYER_COUNT);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Player newPlayer = queue.poll(TIMEOUT, TimeUnit.SECONDS);
                if (newPlayer == null) {
                    if (players.size() > 1 && players.size() < PLAYER_COUNT) {
                        for (Player player :
                                players) {
                            player.setGameId(gameId);
                            playerDao.insert(player);
                        }
                        log.info("timeout, game started with " + players.size() + " players, pushed to DB");
//                      FIXME: client.start(gameId);
                        players.clear();
                    }
                    else
                        log.info("not enough players to start the game");
                } else if (!inGamePlayers.containsKey(newPlayer.getLogin())) {
                    players.add(newPlayer);
                    if (players.size() == 1) {
//                      FIXME: gameId = Long.parseLong(client.create(PLAYER_COUNT));
                        gameId++;
                        log.info("created new game with gameId = " + gameId);
                    }
                    log.info(newPlayer.getLogin() + " added to table");
                    inGamePlayers.put(newPlayer.getLogin(), gameId);
                }
            } catch (InterruptedException e) {
                log.error("queue interrupted");
            }

            if (players.size() == PLAYER_COUNT) {
                for (Player player :
                        players) {
                    player.setGameId(gameId);
                    playerDao.insert(player);
                }
                log.info("game started with maximum players, pushed to DB");
//              FIXME: client.start(gameId);
                players.clear();
            }
        }
    }
}
