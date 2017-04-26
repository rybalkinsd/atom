package ru.atom.lecture10.queue;

/**
 * Created by Ксения on 26.04.2017.
 */
public class PutThread extends Thread {
    BlQueue blockingQueue;

    PutThread(BlQueue b) {
        blockingQueue = b;
    }

    @Override
    public void run() {
        try {
            blockingQueue.put(new Message(Thread.currentThread().getName()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
