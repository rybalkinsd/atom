package websocket;

import client.Action;
import client.Message;
import client.Topic;
import dto.ReplicaDataDto;
import dto.ReplicaDto;
import gamemechanic.InputQueue;
import gamemechanic.Replicator;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;


public class Broker {
    private static final Logger log = LoggerFactory.getLogger(Broker.class);

    private static final Broker instance = new Broker();
    private final ConnectionPool connectionPool;

    public static Broker getInstance() {
        return instance;
    }

    private Broker() {
        this.connectionPool = ConnectionPool.getInstance();
    }

    public void receive(@NotNull WebSocketSession session, @NotNull String msg) {
        //log.info("RECEIVED: " + msg + " | FROM PLAYER: " + ConnectionPool.getInstance().getPlayer(session));
        InputQueue.getInstance().add(new Action(Replicator.toMessage(msg),
                ConnectionPool.getInstance().getPlayer(session)));
    }

    public void send(@NotNull String player,  @NotNull Object object) {
        String message = Replicator.toJson(object);
        WebSocketSession session = connectionPool.getSession(player);
        connectionPool.send(session, message);
    }

    public void broadcast(@NotNull Object object) {
        String message = Replicator.toJson(object);
        //log.info("Broadcast REPLICA: {}",  message);
        connectionPool.broadcast(message);
    }
}
