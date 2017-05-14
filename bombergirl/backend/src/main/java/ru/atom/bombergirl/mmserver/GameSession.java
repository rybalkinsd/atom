package ru.atom.bombergirl.mmserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.bombergirl.gamemodel.geometry.Point;
import ru.atom.bombergirl.gamemodel.model.*;
import ru.atom.bombergirl.message.ObjectMessage;
import ru.atom.bombergirl.message.Topic;
import ru.atom.bombergirl.network.Broker;
import ru.atom.bombergirl.util.JsonHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ikozin on 17.04.17.
 */
public class GameSession implements Tickable, Runnable {
    private static final Logger log = LogManager.getLogger(MatchMaker.class);
    private static AtomicLong idGenerator = new AtomicLong();
    private List<GameObject> gameObjects = new ArrayList<>();
    private static AtomicInteger counter = new AtomicInteger(0);
    private List<ObjectMessage> objectMessages = new ArrayList<>();

    public static final int PLAYERS_IN_GAME = 4;

    private final Connection[] connections;
    private final long id = idGenerator.getAndIncrement();

    private static List<GameObject> gameField = new ArrayList<>();

    static {
        for (int i = 0;i < 17;i++) {
            for (int j = 0;j < 13;j++) {
                if (i == 1 && j == 1
                        || i == 1 && j == 11
                        || i == 15 && j == 1
                        || i == 15 && j == 11
                        || i == 1 && j == 2
                        || i == 2 && j == 1
                        || i == 14 && j == 1
                        || i == 15 && j == 2
                        || i == 1 && j == 10
                        || i == 2 && j == 11
                        || i == 14 && j == 11
                        || i == 15 && j == 10)
                    continue;
                else if (i % 2 == 0 && j % 2 == 0
                        || i == 0
                        || j == 0
                        || i == 16
                        || j == 12)
                    gameField.add(new Wall(GameField.GRID_SIZE * i, GameField.GRID_SIZE * j));
                else
                    gameField.add(new Wood(GameField.GRID_SIZE * i, GameField.GRID_SIZE * j));
            }
        }
    }

    private static List<Point> spawnPositions = new ArrayList<>(Arrays.asList(
            new Point(GameField.GRID_SIZE * (1 + 1/2), GameField.GRID_SIZE * (1 + 1/2)),
            new Point(GameField.GRID_SIZE * (1 + 1/2), GameField.GRID_SIZE * (11 + 1/2)),
            new Point(GameField.GRID_SIZE * (15 + 1/2), GameField.GRID_SIZE * (1 + 1/2)),
            new Point(GameField.GRID_SIZE * (15 + 1/2), GameField.GRID_SIZE * (11 + 1/2))
    ));

    public GameSession(Connection[] connections) {
        this.connections = connections;
    }

    public static int nextValue() {
        return counter.getAndIncrement();
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    @Override
    public void tick(long elapsed) {
        //log.info("tick");
        objectMessages.clear();
        gameObjects.forEach(x -> objectMessages.add(
                new ObjectMessage(x.getClass().getSimpleName(), x.getId(),
                        ((Positionable)x).getPosition())));
        Broker.getInstance().broadcast(Topic.REPLICA,  objectMessages);
        ArrayList<Temporary> dead = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Tickable) {
                ((Tickable) gameObject).tick(elapsed);
            }
            if (gameObject instanceof Temporary && ((Temporary) gameObject).isDead()) {
                dead.add((Temporary)gameObject);
            }
        }
        gameObjects.removeAll(dead);
    }

    public void run() {
        gameObjects.addAll(gameField);
        for (int i = 0; i < connections.length; i++) {
            Pawn pawn = new Pawn(spawnPositions.get(i), this);
            connections[i].setGirl(pawn);
            log.info("set pawn : " + connections[i].getPawn());
            Broker.getInstance().send(connections[i], Topic.POSSESS, pawn.getId());
            gameObjects.add(pawn);
        }
        gameObjects.forEach(x -> objectMessages.add(
                new ObjectMessage(x.getClass().getSimpleName(), x.getId(), ((Positionable)x).getPosition())));
        log.info(JsonHelper.toJson(objectMessages));
        Broker.getInstance().broadcast(Topic.REPLICA,  objectMessages);

        log.info(Thread.currentThread().getName() + " started");
        Ticker ticker = new Ticker(this);
        ticker.loop();
    }

    @Override
    public String toString() {
        return "GameSession{" +
                "connections=" + Arrays.toString(connections) +
                ", id=" + id +
                '}';
    }

    public long getId() {
        return id;
    }
}
