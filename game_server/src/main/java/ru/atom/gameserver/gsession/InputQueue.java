package ru.atom.gameserver.gsession;

import ru.atom.gameserver.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class InputQueue implements MessagesOffering {

    private final Queue<Message> messageQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void offerMessage(Message message) {
        messageQueue.offer(message);
    }

    public List<Message> pollMessages() {
        List<Message> messageList = new ArrayList<>();
        synchronized (messageQueue) {
            while (!messageQueue.isEmpty()) {
                messageList.add(messageQueue.poll());
            }
        }
        return messageList;
    }
}
