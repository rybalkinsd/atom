package ru.atom.lecture10.queue;


import java.util.LinkedList;

/**
 * Created by Ксения on 26.04.2017.
 */
public class BlQueue<T> implements BlockingQueue<T> {
    private static Object lock = new Object();
    private LinkedList<T> array = new LinkedList<T>();
    private int capacity;

    BlQueue(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void put(T elem) throws InterruptedException {
        synchronized (lock) {
            while (capacity == size()) {
                lock.wait();
            }
            array.add(elem);
            lock.notifyAll();
        }
    }

    @Override
    public T take() throws InterruptedException {
        T elem = null;
        synchronized (lock) {
            while (size() == 0) {
                lock.wait();
            }
            elem = array.poll();
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
        return array.size();
    }
}
