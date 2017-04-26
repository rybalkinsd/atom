package ru.atom.lecture10.queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by pavel on 26.04.17.
 */
public class MyBlockingQueue<T> implements BlockingQueue<T> {

    private final Logger log = LogManager.getLogger(MyBlockingQueue.class);
    private final Object lock = new Object();
    private Queue<T> queue;
    private final int CAPACITY;
    private volatile int size;

    public MyBlockingQueue(int capacity) {
        this.CAPACITY = capacity;
        this.size = 0;
        queue = new LinkedList<T>();
    }

    @Override
    public void put(T elem) throws InterruptedException {
        synchronized (lock) {
            while (size == CAPACITY) {
                log.info("Queue blocked");
                lock.wait();
            }
            lock.notifyAll();
            queue.offer(elem);
            size++;
            log.info("Element added to queue");
        }
    }

    @Override
    public T take() throws InterruptedException {
        synchronized (lock) {
            while (size == 0) {
                log.info("Queue blocked");
                lock.wait();
            }
            lock.notifyAll();
            size--;
            log.info("Element removed from queue");
            return queue.poll();
        }
    }

    @Override
    public int remainingCapacity() {
        return CAPACITY - size();
    }

    @Override
    public int size() {
        return size;
    }
}
