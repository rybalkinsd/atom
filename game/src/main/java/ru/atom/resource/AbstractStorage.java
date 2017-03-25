package ru.atom.resource;


import jersey.repackaged.com.google.common.base.Joiner;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Map;
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
        return "[{" + Joiner.on("}, {").join(memory.keySet()) + "}]";
    }

    public ArrayList<K> getAll() {
        ArrayList<K> result = new ArrayList<>();
        for (K key : memory.keySet()) {
            result.add(key);
        }
        return result;
    }
}
