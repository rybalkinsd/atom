package ru.atom.lecture10.queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;

/**
 * Created by dmitriy on 26.04.17.
 */
public class BlockingQueueImpl<T> implements BlockingQueue<T> {
    private static final Logger log = LogManager.getLogger(BlockingQueueImpl.class);
    private final Object lock = new Object();
    private final int capacity;
    private int size = 0;
    private LinkedList<T> blockingQueue;

    public BlockingQueueImpl(int capacity) {
        this.capacity = capacity;
        blockingQueue = new LinkedList<T>();
    }

    @Override
    public void put(T elem) throws InterruptedException {
        synchronized (lock) {
            while (remainingCapacity() == 0) {
                log.info("waiting for delete element");
                lock.wait();
            }
            lock.notifyAll();
            blockingQueue.add(elem);
            log.info("offer element : " + elem);
            size++;
        }
    }

    @Override
    public T take() throws InterruptedException {
        synchronized (lock) {
            while (size == 0) {
                lock.wait();
                log.info("waiting for adding element");
            }
            lock.notifyAll();
            size--;
            log.info("get and remove object");
            return blockingQueue.pollFirst();
        }
    }

    @Override
    public int remainingCapacity() {
        synchronized (lock) {
            return capacity - size;
        }
    }

    @Override
    public int size() {
        synchronized (lock) {
            return size;
        }
    }
}
