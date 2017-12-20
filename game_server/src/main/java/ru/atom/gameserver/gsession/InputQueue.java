package ru.atom.gameserver.gsession;

import ru.atom.gameserver.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

class InputQueue implements MessagesOffering {

    private final BlockingQueue<Message> messageQueue = new ArrayBlockingQueue<>(16);

    @Override
    public void offerMessage(Message message) {
        messageQueue.offer(message);
    }

    public List<Message> pollMessages() {
        List<Message> messageList = new ArrayList<>();
        messageQueue.drainTo(messageList);
        return messageList;
    }
}
