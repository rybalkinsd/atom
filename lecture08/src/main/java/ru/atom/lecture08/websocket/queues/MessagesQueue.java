package ru.atom.lecture08.websocket.queues;

import org.springframework.stereotype.Repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class MessagesQueue {
    static private Queue<String> messages = new ConcurrentLinkedQueue<>();

    static public Queue<String> getMessages() {
        return messages;
    }
}
