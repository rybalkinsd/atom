package ru.atom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TokenStorage {
    private ConcurrentHashMap<Long, String> storage = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Long, String> getStorage() {
        return storage;
    }

    public Long getTokenByName(String name) {
        for (Map.Entry<Long, String> entry : storage.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean validateToken(Long token) {
        return storage.containsKey(token);
    }
}
