package ru.atom.dbhackaton.resource;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by BBPax on 24.03.17.
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

    // TODO: 13.04.17 wrong implemention
    public String toString() {
        return String.join(", ", memory.values().stream().toString());
    }

    public LinkedList<V> getAll() {
        LinkedList<V> result = new LinkedList<>();
        for (V value : memory.values()) {
            result.add(value);
        }
        return result;
    }
}
