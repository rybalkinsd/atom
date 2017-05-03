package ru.atom.lecture10.queue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arif on 26.04.2017.
 */
public class BlockingQueueImp<T> implements BlockingQueue<T> {
    private final int capacity;
    private int size;
    private List<T> queue;
    private static final Object lock = new Object();

    public BlockingQueueImp(int capacity) {
         queue = new ArrayList<T>();
         this.capacity = capacity;
    }

    @Override
    public void put(T elem) throws InterruptedException {
        synchronized (lock) {
            if (size == capacity) {
                elem.wait();
            }

        }
    }

    @Override
    public T take() throws InterruptedException {
        return null;
    }

    @Override
    public int remainingCapacity() {
        return 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    public int getCapacity() {
        return capacity;
    }

}
