package threads;

import boxes.ConnectionPool;
import gameserver.Ticker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;

import boxes.GameStarterQueue;

import gameobjects.GameSession;



public class GameMechanics implements Runnable {
    private static final Logger log = LogManager.getLogger(GameMechanics.class);
    private static AtomicLong idGenerator = new AtomicLong();
    private int playerCount;
    private long id = idGenerator.getAndIncrement() + 1;

    public GameMechanics(int playerCount) {
        this.playerCount = playerCount;
    }

    @Override
    public void run() {

        while (!GameStarterQueue.getInstance().contains((int) id)) {
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(100));
        }
        while (ConnectionPool.getInstance().getPlayersWithGameId((int) id).size() < playerCount) {
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(100));
        }
        GameStarterQueue.getInstance().poll();
        log.info("Game {} started", id);
        log.info("Players in this session:");
        ConcurrentLinkedQueue playerQueue = ConnectionPool.getInstance().getPlayersWithGameId((int) id);
        while (!playerQueue.isEmpty()) {
            log.info(playerQueue.poll());
        }
        Ticker ticker = new Ticker();
        GameSession gameSession = new GameSession((int) id, ticker, playerCount);
        gameSession.initGameArea();
        /*for (int y = 0; y < 13; y++) {
            for (int x = 0; x < 17; x++)
                System.out.print(gameSession.getCellFromGameArea(x, y).getState());
            System.out.print("\n");}*/  //Proverka zapolnenia pol`a
        ticker.gameLoop(gameSession);
        log.info("Game #{} over", id);
    }

    public long getId() {
        return id;
    }
}
