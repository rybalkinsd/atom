package ru.atom.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ru.atom.model.Level;
import ru.atom.model.World;
import ru.atom.model.object.actor.Pawn;
import ru.atom.network.Player;
import ru.atom.network.message.Broker;
import ru.atom.network.message.Topic;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by sergey on 2/3/17.
 */
public class GameSession implements Runnable {
    private final static Logger log = LogManager.getLogger(GameSession.class);
    private static final int FPS = 60;
    private final static long FRAME_TIME = 1000 / FPS;
    private final Broker broker;
    private final Level level;
    private final List<Player> players;
    private World world;
    private long tickNumber = 0;


    GameSession(@NotNull List<Player> players, Level level) {
        this.players = players;
        this.level = level;
        this.broker = Broker.getInstance();
    }

    @Override
    public void run() {
        world = new World(level);

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Pawn pawn = Pawn.create(level.getSpawnPlaces().get(i));
            player.setPawn(pawn);
            broker.send(player, Topic.POSSESS, pawn.getId());
        }

        while (!Thread.currentThread().isInterrupted()) {
            long started = System.currentTimeMillis();
            act(FRAME_TIME);
            long elapsed = System.currentTimeMillis() - started;
            if (elapsed < FRAME_TIME) {
                log.info("All tick finish at {} ms", elapsed);
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(FRAME_TIME - elapsed));
            } else {
                log.warn("tick lag {} ms", elapsed - FRAME_TIME);
            }
            log.info("{}: tick ", tickNumber);
            tickNumber++;
        }
    }

    private void act(long time) {
        world.tick(time);
        broker.broadcast(Topic.REPLICA, world);
        if (world.isGameOver()) {
            //broker.broadcast(Topic.END_MATCH, world.findWinner());
        }
    }

}
