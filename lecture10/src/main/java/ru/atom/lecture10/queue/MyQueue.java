package ru.atom.lecture10.queue;

import java.util.ArrayList;

/**
 * Created by gammaker on 26.04.2017.
 */
public class MyQueue<T> implements BlockingQueue<T> {
    private ArrayList<T> array;
    private int first;
    private int size;

    public MyQueue(int capacity) {
        array = new ArrayList<>(capacity);
        first = 0;
        for (int i = 0; i < capacity; i++) {
            array.add(null);
        }
        size = 0;
    }

    @Override
    public void put(T elem) throws InterruptedException {
        synchronized (array) {
            while (array.size() == size) array.wait();
            array.set(first + size++, elem);
            array.notify();
        }
    }

    @Override
    public T take() throws InterruptedException {
        synchronized (array) {
            while (size == 0) array.wait();
            T result = array.get(first++ % array.size());
            size--;
            array.notify();
            return result;
        }
    }

    @Override
    public int remainingCapacity() {
        return array.size() - size;
    }

    @Override
    public int size() {
        return size;
    }
}
