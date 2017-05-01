package ru.atom.lecture10.queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by konstantin on 26.04.17.
 */
public class RealisationBlockingQueue<T> implements BlockingQueue<T> {

    private static final Logger log = LogManager.getLogger(RealisationBlockingQueue.class);
    private static final Object lock = new Object();
    private Queue<T> queue;
    private final int capacity;
    private int size;

    public RealisationBlockingQueue() {
        queue = new LinkedList<T>();
        capacity = 4;
        size = 0;
    }

    public RealisationBlockingQueue(int capacity) {
        queue = new LinkedList<T>();
        this.capacity = capacity;
        this.size = 0;
    }

    @Override
    public void put(T elem) throws InterruptedException {
        synchronized (lock) {
            while (size() == capacity) {
                log.info("Put blocked");
                lock.wait();
            }
            lock.notifyAll();
            queue.offer(elem);
            size++;
            log.info("Put successful");
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T take() throws InterruptedException {
        synchronized (lock) {
            while (size() == 0) {
                log.info("Take blocked");
                lock.wait();
            }
            lock.notifyAll();
            size--;
            log.info("Take successful");
            return queue.poll();
        }
    }

    @Override
    public int remainingCapacity() {
        return (capacity - size);
    }
}
