package gamemechanic;

import dto.PossesDto;
import dto.ReplicaDto;
import gamesession.GameSession;
import geometry.Point;
import objects.Bomb;
import objects.Player;
import objects.Fire;
import objects.GameObject;
import objects.Wood;
import objects.Tickable;
import objects.Movable;
import objects.Wall;
import org.slf4j.LoggerFactory;
import websocket.Broker;
import websocket.ConnectionPool;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Ticker {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Ticker.class);
    private static final int FPS = 60;
    private static final long FRAME_TIME = 1000 / FPS;
    private static final Set<Tickable> tickables = new ConcurrentSkipListSet<>();
    private static long tickNumber = 0;

    public void gameLoop() {
        GameMechanics gameMechanics = new GameMechanics();
        while (!Thread.currentThread().isInterrupted()) {
            gameMechanics.readQueue(); /*read actions**/
            gameMechanics.changeTickables(); /*prepare tickables for mechanic**/
            act(FRAME_TIME);   /*change all tickables**/
            gameMechanics.tick(FRAME_TIME);/*do mechanic**/
            long started = System.currentTimeMillis();
            Broker.getInstance().broadcast(new ReplicaDto(GameSession.getAllDto(), false));
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

    public static void registerTickable(Tickable tickable) {
        tickables.add(tickable);
    }

    public void unregisterTickable(Tickable tickable) {
        tickables.remove(tickable);
    }

    public static long getTickNumber() {
        return tickNumber;
    }

    private void act(long elapsed) {
        for (Player player: GameSession.getMapPlayers()) {
            player.tick(elapsed);
        }
        for (Bomb bomb: GameSession.getMapBombs()) {
            bomb.tick(elapsed);
        }
        for (Fire fire: GameSession.getMapFire()) {
            fire.tick(elapsed);
        }

        //tickables.forEach(tickable -> tickable.tick(elapsed));
    }

}
