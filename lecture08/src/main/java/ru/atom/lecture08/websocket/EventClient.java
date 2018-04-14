package ru.atom.lecture08.websocket;

import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import ru.atom.lecture08.websocket.message.Message;
import ru.atom.lecture08.websocket.util.JsonHelper;

import java.io.IOException;

public class EventClient {
    public static void main(String[] args) {
        // connection url
        String uri = "ws://54.224.37.210:8090/events";

        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketSession session = null;
        try {
            // The socket that receives events
            EventHandler socket = new EventHandler();
            // Make a handshake with server
            ListenableFuture<WebSocketSession> fut = client.doHandshake(socket, uri);
            // Wait for Connect
            session = fut.get();
            // Send a message

            //session.sendMessage(new TextMessage("Hello"));
            Message my = new Message("HELLO", "ArtemTashevtsev");
            session.sendMessage(new TextMessage(JsonHelper.toJson(my)));
            // Close session
            session.close();

        } catch (Throwable t) {
            t.printStackTrace(System.err);
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (IOException ignored) {
            }
        }
    }
}
