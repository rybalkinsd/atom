package ru.atom.lecture10.queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Queue;

public class BlockingQueueImpl<T> implements BlockingQueue<T> {
    private static final Logger log = LogManager.getLogger(BlockingQueueImpl.class);
    private final Queue<T> queue = new ArrayDeque<>();
    private final int capacity;
    private final Object lock = new Object();

    public BlockingQueueImpl(int capacity) {
        this.capacity = capacity;
    }


    @Override
    public void put(T elem) throws InterruptedException {
        synchronized (lock) {
            try {
                while (capacity == queue.size()) {
                    log.info(" put thread wait");
                    lock.wait();
                }
                queue.add(elem);
            } finally {
                log.info("notify after put");
                lock.notifyAll();
            }
        }
    }

    @Override
    public T take() throws InterruptedException {
        synchronized (lock) {
            try {
                while (queue.size() == 0) {
                    log.info(" take thread wait");
                    lock.wait();
                }
                return queue.poll();
            } finally {
                log.info("notify after take");
                lock.notifyAll();
            }
        }
    }

    @Override
    public int remainingCapacity() {
        synchronized (lock) {
            return capacity - queue.size();
        }
    }

    @Override
    public int size() {
        synchronized (lock) {
            return queue.size();
        }
    }
}
