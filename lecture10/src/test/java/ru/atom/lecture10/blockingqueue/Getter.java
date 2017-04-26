package ru.atom.lecture10.blockingqueue;

import ru.atom.lecture10.queue.BlockingQueue;

import java.util.ArrayList;

/**
 * Created by kinetik on 26.04.17.
 */
public class Getter<E> extends Thread {

    private final BlockingQueue<E> queue;
    private ArrayList<E> objects = new ArrayList<>();

    public Getter(BlockingQueue<E> queue) {
        this.queue = queue;
    }

    public int getSize() {
        return this.objects.size();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && queue.size() != 0) {
            try {
                objects.add(queue.take());
                sleep(5);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
