package ru.atom.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.WebSocketSession;
import ru.atom.input.InputMessages;
import ru.atom.message.Message;
import ru.atom.message.Topic;
import ru.atom.model.GameModel;
import ru.atom.model.GameObject;
import ru.atom.model.GameServerParams;
import ru.atom.model.Movable;
import ru.atom.network.Broker;
import ru.atom.network.ConnectionPool;
import ru.atom.tick.Tickable;
import ru.atom.tick.Ticker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class GameSession implements GameObject, Tickable, Comparable<GameObject> {
    private static final Logger log = LogManager.getLogger(GameSession.class);
    private static AtomicLong idGenerator = new AtomicLong();

    private long id = idGenerator.getAndIncrement();
    private int playersAmount = 0;
    private ArrayList<Player> players = new ArrayList<>();
    private Ticker ticker = new Ticker();
    private GameModel gameModel;
    private Message backGroundReplica;
    private int waitingTime = 0;
    boolean possessed = false;

    public long getId() {
        return this.id;
    }

    public GameSession(int playersAmount) {
        this.playersAmount = playersAmount;
        gameModel = new GameModel(ticker, playersAmount);
        //backGroundReplica = Replicator.getBackGroundReplica(gameModel);
        ticker.registerTickable(this);
        ticker.start();

    }




    public synchronized boolean add(String playerName) {
        Player player = new Player(gameModel.getPlayerObjectId(players.size()), playerName);
        if (players.size() == playersAmount) {
            return false;
        }
        players.add(player);

        log.info("New player name: " + playerName + " playerId: " + player.getId());
        return true;
    }

    public void processInput(Player player, long elaspsed) {
        Vector<Message> messages = new Vector<Message>(InputMessages.getInstance()
                .get(player.getName()));
        InputMessages.getInstance().get(player.getName()).clear();
        Message moveMessage = null;
        Message bombMessage = null;

        for (Message message : messages) {
            switch (message.getTopic()) {
                case PLANT_BOMB: {
                    if (bombMessage == null) {
                        bombMessage = message;
                    }
                    break;
                }

                case MOVE: {
                    moveMessage = message;
                }
                break;

                default: {

                }
            }
        }

        if (bombMessage != null) {
            gameModel.handleBombEvent(player.getId());
        }

        if (moveMessage != null) {
            switch (moveMessage.getData()) {
                case "{\"direction\":\"RIGHT\"}": {
                    gameModel.handleMoveEvent(player.getId(), Movable.Direction.RIGHT, elaspsed);
                    break;
                }
                case "{\"direction\":\"LEFT\"}": {
                    gameModel.handleMoveEvent(player.getId(), Movable.Direction.LEFT, elaspsed);
                    break;
                }
                case "{\"direction\":\"DOWN\"}": {
                    gameModel.handleMoveEvent(player.getId(), Movable.Direction.DOWN, elaspsed);
                    break;
                }
                case "{\"direction\":\"UP\"}": {
                    gameModel.handleMoveEvent(player.getId(), Movable.Direction.UP, elaspsed);
                    break;
                }
                default: {
                    log.warn("Incorrect moveMessage data");
                }
            }
        }
    }

    private Player getPlayer(String playerName) {
        return players.stream().filter(player -> player.getName().equals(playerName)).findFirst().get();
    }

    public void removePlayer(String playerName) {
        Player player = getPlayer(playerName);
        if (player != null) {
            gameModel.removePlayer(player.getId());
        }
    }

    private void handleGameOver() {
        ticker.unregisterTickable(this);
        players.forEach(player -> ConnectionPool.getInstance().remove(player.getName()));
        GameServerService.removeGameSession(id);
    }

    @Override
    public void tick(long elapsed) {
        if (players.size() < playersAmount) {
            waitingTime += elapsed;
            if (waitingTime >= GameServerParams.getInstance().getTimeOut()) {
                handleGameOver();
                return;
            }
        } else if (players.size() == playersAmount && possessed) {
            players.forEach(player -> {
                Broker.getInstance().send(player.getName(), Replicator.getReplica(gameModel));
            });
            if (gameModel.isGameOver()) {
                handleGameOver();
                return;
            }
            gameModel.update();
            players.forEach(player -> {
                processInput(player, elapsed);
            });
        } else if (players.size() == playersAmount && !possessed) {
            players.forEach(player -> {
                Broker.getInstance().send(player.getName(), Topic.POSSESS, player.getId());
                Broker.getInstance().send(player.getName(), Replicator.getReplicaWithBack(gameModel));
            });
            gameModel.update();
            players.forEach(player -> {
                processInput(player, elapsed);
            });
            possessed = true;

        }

    }

    @Override
    public int compareTo(GameObject that) {
        return (int)(this.id - that.getId());
    }



}
