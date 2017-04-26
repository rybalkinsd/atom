package ru.atom.lecture10.queue;

import java.util.LinkedList;

/**
 * Created by kinetik on 26.04.17.
 */
public class BlockingQueueOwn<E> implements BlockingQueue<E> {

    private final LinkedList<E> inQueue;
    private final int capacity;
    private final Object lock = new Object();

    public BlockingQueueOwn(int capacity) {
        this.inQueue = new LinkedList<>();
        this.capacity = capacity;
    }

    @Override
    public void put(E elem) throws InterruptedException {
        synchronized (lock) {
            if (elem != null) {
                if (this.size() < this.capacity) {
                    this.inQueue.add(elem);
                } else {
                    try {
                        lock.wait();
                    } catch (InterruptedException ex) {
                        throw new InterruptedException();
                    }
                }
            }
        }
    }

    @Override
    public E take() throws InterruptedException {
        synchronized (lock) {
            while (this.size() == 0) {
                try {
                    lock.wait();
                } catch (InterruptedException ex) {
                    throw new InterruptedException();
                }
            }
            E head = this.inQueue.get(0);
            this.inQueue.remove(0);
            lock.notifyAll();
            return head;
        }
    }

    @Override
    public int remainingCapacity() {
        return this.capacity - this.size();
    }

    @Override
    public int size() {
        return this.inQueue.size();
    }
}
