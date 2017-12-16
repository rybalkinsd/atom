package gamemechanic;

import client.Action;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InputQueue {

    private static Queue<Action> instance = new ConcurrentLinkedQueue<>();

    public static Queue<Action> getInstance() {
        return instance;
    }
}
