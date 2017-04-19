package ru.atom.lecture08.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.client.masks.ZeroMasker;

import java.net.URI;
import java.util.concurrent.Future;

public class EventClient {
    public static void main(String[] args) {
        URI uri = URI.create("ws://localhost:8090/events/");//CHANGE TO wtfis.ru for task

        WebSocketClient client = new WebSocketClient();
        //client.setMasker(new ZeroMasker());
        try {
            try {
                client.start();
                // The socket that receives events
                EventHandler socket = new EventHandler();
                // Attempt Connect
                Future<Session> fut = client.connect(socket, uri);
                // Wait for Connect
                Session session = fut.get();
                // Send a message
                //TODO TASK: implement sending Message with type HELLO and your name as data
                session.getRemote().sendString("Hello");
                // Close session
                session.close();
            } finally {
                client.stop();
            }
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
