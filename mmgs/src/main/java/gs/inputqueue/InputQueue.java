package gs.inputqueue;

import gs.message.Message;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentLinkedQueue;

public class InputQueue {
    private static ConcurrentLinkedQueue<Message> queue = new ConcurrentLinkedQueue<Message>();

    public void addToQueue(WebSocketSession ses, Message message) {
        queue.offer(message);
    }

    public static ConcurrentLinkedQueue<Message> getQueue() {
        return queue;
    }
}
