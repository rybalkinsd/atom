package ru.atom.websocket.server;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import ru.atom.websocket.message.Message;
import ru.atom.websocket.message.Topic;
import ru.atom.websocket.network.Broker;
import ru.atom.websocket.network.ConnectionPool;
import ru.atom.websocket.util.JsonHelper;

import java.util.Random;

import static java.lang.Math.abs;

public class EventHandler extends WebSocketAdapter {
    @Override
    public void onWebSocketConnect(Session sess) {
        super.onWebSocketConnect(sess);
        System.out.println("Socket Connected: " + sess);
        //Broker.getInstance().receive(sess, JsonHelper.toJson(new Message(Topic.HELLO,
        //        "Vlad" + abs(new Random().nextInt()))));
    }

    @Override
    public void onWebSocketText(String message) {
        super.onWebSocketText(message);
        Broker.getInstance().receive(super.getSession(), message);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        Session session = super.getSession();
        super.onWebSocketClose(statusCode, reason);
        System.out.println("Socket Closed: [" + statusCode + "] " + reason);
        Broker.getInstance().receive(session,
                JsonHelper.toJson(new Message(Topic.FINISH, "onWebSocketClose")));
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        Session session = super.getSession();
        super.onWebSocketError(cause);
        cause.printStackTrace(System.err);
        Broker.getInstance().receive(session,
                JsonHelper.toJson(new Message(Topic.FINISH, "onWebSocketError")));
    }
}
