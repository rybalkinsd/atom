package gs.tick;


import gs.GameMechanics;
import gs.gamerepository.GameController;
import gs.matchmakerrequest.MatchMakerRequest;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Ticker {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Ticker.class);
    private static final int FPS = 60;
    private static final long FRAME_TIME = 6000 / FPS;
    private Set<Tickable> tickables = new ConcurrentSkipListSet<>();
    private long tickNumber = 0;

    public void gameLoop() {
        while (!Thread.currentThread().isInterrupted()) {
            if (GameController.getGameMechanics().getGs().isGameSessionIsOver()) {
                GameController.setGameMechanics(null);
                Thread.currentThread().interrupt();
            } else {
                long started = System.currentTimeMillis();
                //act(FRAME_TIME);
                GameController.getGameMechanics().tick(FRAME_TIME);
                long elapsed = System.currentTimeMillis() - started;
                if (elapsed < FRAME_TIME) {
                    //log.info("All tick finish at {} ms", elapsed);
                    LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(FRAME_TIME - elapsed));
                } else {
                    log.warn("tick lag {} ms", elapsed - FRAME_TIME);
                }
                //log.info("{}: tick ", tickNumber);
                tickNumber++;
            }
        }
    }

    public void registerTickable(Tickable tickable) {
        tickables.add(tickable);
    }

    public void unregisterTickable(Tickable tickable) {
        tickables.remove(tickable);
    }

    private void act(long elapsed) {
        tickables.forEach(tickable -> tickable.tick(elapsed));
    }

    public long getTickNumber() {
        return tickNumber;
    }
}
