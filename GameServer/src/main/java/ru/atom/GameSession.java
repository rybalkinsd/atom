package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.atom.model.FormedGameObject;
import ru.atom.model.GameModel;
import ru.atom.model.Girl;
import ru.atom.model.Movable;
import ru.atom.util.JsonHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class GameSession implements Tickable {
    private static final Logger log = LogManager.getLogger(GameSession.class);
    private static AtomicLong idGenerator = new AtomicLong();

    private GameModel gameModel = new GameModel();
    private int playersAmount = 0;
    private ArrayList<Connection> pool;
    private long id = idGenerator.getAndIncrement();

    public long getId() {
        return this.id;
    }

    public GameSession(int playersAmount) {

        pool = new ArrayList<>(playersAmount);
        this.playersAmount = playersAmount;
    }

    public synchronized static void send(@NotNull WebSocketSession session, @NotNull Message msg) {
        String message = JsonHelper.toJson(msg);
        if (session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException ignored) {
            }
        }
    }

    public void broadcast(Message msg) {
        pool.stream().forEach(connection -> send(connection.getSession(), msg));
    }

    public void shutdown() {
        pool.forEach(connection -> {
            if (connection.getSession().isOpen()) {
                try {
                    connection.getSession().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public synchronized long add(String name, WebSocketSession session) {
        Connection connection = new Connection(gameModel.getPlayerObjectId(pool.size()), name, session);
        if (pool.size() != playersAmount) {
            pool.add(connection);
            log.info("New connection name: " + name + " playerId: " + connection.getPlayerId());
            if (pool.size() == playersAmount) {
                GameServerService.subscribeTickEvent(this);
            }
        }
        return connection.getPlayerId();
    }

    public void remove(WebSocketSession session) {
        pool.remove(session);
    }

    public void handleInput(Connection connection, long elaspsed) {
        log.info("input for " + connection.getPlayerName() + ": " + InputMessages.getInstance()
                .get(connection.getPlayerName()).size());
        Vector<Message> messages = new Vector<>(InputMessages.getInstance()
                .get(connection.getPlayerName()));
        InputMessages.getInstance().get(connection.getPlayerName()).clear();
        log.info("input for " + connection.getPlayerName() + ": " + InputMessages.getInstance()
                .get(connection.getPlayerName()).size());
        Message moveMessage = null;
        Message bombMessage = null;
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getTopic() == Topic.PLANT_BOMB && bombMessage == null) {
                bombMessage =  messages.get(i);
                continue;
            }
            if (messages.get(i).getTopic() == Topic.MOVE
                    && (bombMessage != null || i == messages.size() - 1)) {
                moveMessage = messages.get(i);
                continue;
            }
        }
        if (bombMessage != null) {
            gameModel.handleBombEvent(connection.getPlayerId());
        }
        if (moveMessage != null) {
            switch (moveMessage.getData()) {
                case "{\"direction\":\"RIGHT\"}": {
                    gameModel.handleMoveEvent(connection.getPlayerId(), Movable.Direction.RIGHT, elaspsed);
                    break;
                }
                case "{\"direction\":\"LEFT\"}": {
                    gameModel.handleMoveEvent(connection.getPlayerId(), Movable.Direction.LEFT, elaspsed);
                    break;
                }
                case "{\"direction\":\"DOWN\"}": {
                    gameModel.handleMoveEvent(connection.getPlayerId(), Movable.Direction.DOWN, elaspsed);
                    break;
                }
                case "{\"direction\":\"UP\"}": {
                    gameModel.handleMoveEvent(connection.getPlayerId(), Movable.Direction.UP, elaspsed);
                    break;
                }
                default: {

                }
            }
        }


    }

    public Message getGameModelSnapshot() {
        String replica = "{\"objects\":[";

        replica = replica.concat(gameModel.players.entrySet().stream().map(girlEntry ->
                girlEntry.getValue().toString()).collect(Collectors.joining(",")).toString());
        replica = replica.concat("],\"gameOver\":false}");
        return new Message(Topic.REPLICA, replica);

    }


    @Override
    public void tick(long elapsed) {
        if (pool.size() == playersAmount) {
            for (Connection connection : pool) {
                handleInput(connection, elapsed);
                send(connection.getSession(), getGameModelSnapshot());

            }
        } else {
            GameServerService.unSubscribeTickEvent(this);
        }
        gameModel.tick(elapsed);

    }


}
