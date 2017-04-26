package ru.atom.lecture10.queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by BBPax on 26.04.17.
 */
public class BlockingQueueImpl<T> implements BlockingQueue<T> {

    private static final Logger log = LogManager.getLogger(BlockingQueueImpl.class);
    private final int capacity;
    private static final Object lock = new Object();
    private Queue<T> container;

    public BlockingQueueImpl() {
        container = new ArrayDeque<T>();
        capacity = 5;
    }

    public BlockingQueueImpl(int capacity) {
        container = new ArrayDeque<T>();
        this.capacity = capacity;
    }

    @Override
    public void put(T elem) throws InterruptedException {
        synchronized (lock) {
            while (remainingCapacity() == 0) {
                log.info("put was blocked");
                lock.wait();
            }
            lock.notifyAll();
            container.offer(elem);
            log.info("successful put");
        }
    }

    @Override
    public T take() throws InterruptedException {
        synchronized (lock) {
            while (size() == 0) {
                log.info("take was blocked");
                lock.wait();
            }
            lock.notifyAll();
            log.info("successful take");
            return container.poll();
        }
    }

    @Override
    public int remainingCapacity() {
        return capacity - size();
    }

    @Override
    public int size() {
        return container.size();
    }
}
