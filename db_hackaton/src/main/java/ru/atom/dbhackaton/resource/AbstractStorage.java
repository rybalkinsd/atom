package ru.atom.dbhackaton.resource;


import jersey.repackaged.com.google.common.base.Joiner;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zarina on 25.03.17.
 */
public abstract class AbstractStorage<K, V> {
    protected ConcurrentHashMap<K, V> memory;

    public AbstractStorage() {
        this.memory = new ConcurrentHashMap<>();
    }

    public boolean put(K k, V v) {
        return memory.put(k, v) == v;
    }

    public V get(K k) {
        return memory.get(k);
    }

    public V remove(K k) {
        return memory.remove(k);
    }

    public int getSize() {
        return memory.size();
    }

    public String toString() {
        return "[" + Joiner.on(", ").join(memory.values()) + "]";
    }

    public LinkedList<V> getAll() {
        LinkedList<V> result = new LinkedList<>();
        for (V value : memory.values()) {
            result.add(value);
        }
        return result;
    }
}
