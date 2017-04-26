package ru.atom.lecture10.blockingqueue;

import ru.atom.lecture10.queue.BlockingQueue;

import java.util.ArrayList;

/**
 * Created by kinetik on 26.04.17.
 */
public class Putter<E> extends Thread {

    private final BlockingQueue<E> queue;
    private ArrayList<E> objects;

    public Putter(BlockingQueue<E> queue, ArrayList<E> objects) {
        this.queue = queue;
        this.objects = objects;
    }

    @Override
    public void run() {
        int objCounter = 0;
        while (!Thread.currentThread().isInterrupted() && objCounter < objects.size()) {
            try {
                E object = (E) new Object();
                queue.put(object);
                objCounter += 1;
                sleep(5);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

}
