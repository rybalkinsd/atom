package gameserver;

import boxes.ConnectionPool;
import message.DirectionMessage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;
import message.Message;
import message.Topic;
import util.JsonHelper;


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

    public Message receive(@NotNull WebSocketSession session, @NotNull String msg) {
        //log.info("RECEIVED: " + msg);
        Message message = JsonHelper.fromJson(msg, Message.class);
        return message;
    }

    public void send(@NotNull String player, @NotNull Topic topic, @NotNull Object object) {
        //String message = "{\"topic\":\"REPLICA\",\"data\":{ \"objects\":[" + object + "], \"gameOver\":true}}";
        String message = "{\"topic\":\"REPLICA\",\"data\":{ \"objects\":" + object + "}}";
        log.info(message);
        WebSocketSession session = connectionPool.getSession(player);
        connectionPool.send(session, message);
    }

    public void broadcast(@NotNull Topic topic, @NotNull Object object) {
        String message = "{\"topic\":\"REPLICA\",\"data\":{ \"objects\":[" + object + "], \"gameOver\":false}}";
        log.info(message);
        connectionPool.broadcast(message);
    }

}