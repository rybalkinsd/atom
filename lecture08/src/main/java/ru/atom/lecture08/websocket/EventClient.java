package ru.atom.lecture08.websocket;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.client.masks.ZeroMasker;
import ru.atom.lecture08.websocket.message.Message;
import ru.atom.lecture08.websocket.message.Topic;

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
                Topic topic = Topic.HELLO;
                Message message = new Message(topic, "Saenko Dmitry");
                ObjectMapper mapper = new ObjectMapper();

                mapper.setVisibility(PropertyAccessor.FIELD,
                        JsonAutoDetect.Visibility.ANY);

                String hello = mapper.writeValueAsString(message);
                session.getRemote().sendString(hello);
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
