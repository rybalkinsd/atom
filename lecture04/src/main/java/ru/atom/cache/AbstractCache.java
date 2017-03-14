package ru.atom.cache;

public abstract class AbstractCache<K, V> {
    protected final int capacity;

    public AbstractCache(int capacity) {
        this.capacity = capacity;
    }

    public abstract boolean put(K k, V v);

    public abstract V get(K k);

    public abstract int getSize();

    public int getCapacity() {
        return capacity;
    }
}