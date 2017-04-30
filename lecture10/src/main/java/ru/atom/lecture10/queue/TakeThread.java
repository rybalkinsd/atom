package ru.atom.lecture10.queue;

/**
 * Created by Ксения on 26.04.2017.
 */
public class TakeThread extends Thread {
    BlQueue blockingQueue;

    TakeThread(BlQueue b) {
        blockingQueue = b;
    }

    @Override
    public void run() {
        try {
            blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
