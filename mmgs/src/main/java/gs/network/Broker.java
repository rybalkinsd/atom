package gs.network;

import gs.GameSession;
import gs.message.Message;
import gs.message.Topic;
import gs.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;


public class Broker {
    private static final Logger log = LogManager.getLogger(Broker.class);

    private static final Broker instance = new Broker();
    private final ConnectionPool connectionPool;

    private TextMessage textMessage = new TextMessage("dsfsdfsdfsdf");
    public static Broker getInstance() {
        return instance;
    }

    public Broker() {
        this.connectionPool = ConnectionPool.getInstance();
    }

    public void receive(@NotNull WebSocketSession session, @NotNull String msg) {
        log.info("RECEIVED: " + msg);
        System.out.println("RECEIVED: " + msg);
        Message message = JsonHelper.fromJson(msg, Message.class);
        if (message.getTopic().equals(Topic.MOVE)) {
            handleMove(session, message.getData());
        } else if (message.getTopic().equals(Topic.PLANT_BOMB)) {
            handleBomb(session);
        }
    }

    public void send(@NotNull GameSession gs, @NotNull String player, @NotNull Topic topic, @NotNull String message) {
        //String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        //Message message = new Message(topic, JsonHelper.toJson(object));
        //WebSocketSession session = connectionPool.getSession(player);
        //connectionPool.send(session, message);
        //String msg = "{\"topic\":\"" + topic + "\",\"data\":{\"objects\":[" + message + "]}}";
        //gs.getSession().sendMessage(new TextMessage(msg.toCharArray()));
        try {
            gs.getSession().sendMessage(new TextMessage(JsonHelper.toJson(new Message(topic, message))));;
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }


    /*public void send(@NotNull GameSession gs) {
        String message = "";
        message = "{\"topic\":\"REPLICA\",\"data\":{\"objects\":[" + gs.jsonStringWalls() + "]}}";
        System.out.println(message);
        }*/

    public void handleMove(WebSocketSession session, String direction) {
        Map<String, String> jsonJavaRootObject = JsonHelper.fromJson(direction, Map.class);
        String data = (String) jsonJavaRootObject.get("direction");
    }

    public void handleBomb(WebSocketSession session) {
        log.info("bomb planted");
    }

    public void broadcast(@NotNull Topic topic, @NotNull Object object) {
        String message = JsonHelper.toJson(new Message(topic, JsonHelper.toJson(object)));
        connectionPool.broadcast(message);
    }

}
