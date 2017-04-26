package ru.atom.lecture10.queue;

import java.util.LinkedList;

/**
 * Created by ilysk on 26.04.17.
 */
public class BlockingQueueImpl implements BlockingQueue {

    private final int capacity;
    private final Object lock = new Object();
    private LinkedList rep = new LinkedList();

    public BlockingQueueImpl(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public void put(Object elem) throws InterruptedException {
        synchronized (lock) {
            while (size() >= capacity) {
                lock.wait();
            }
            rep.add(elem);
            lock.notifyAll();
        }
    }

    @Override
    public Object take() throws InterruptedException {
        synchronized (lock) {
            while (size() < capacity) {
                lock.wait();
            }
            Object elem = rep.getFirst();
            rep.removeFirst();
            lock.notifyAll();
            return elem;
        }
    }

    @Override
    public int remainingCapacity() {
        return capacity;
    }

    @Override
    public int size() {
        return rep.size();
    }
}
