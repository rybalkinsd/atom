package ru.atom.dbhackaton.model;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vladfedorenko on 26.03.17.
 */
public class UserStorage {
    private static ConcurrentHashMap<String, User> storage = new ConcurrentHashMap();

    public void addUser(String username, User user) {
        this.storage.put(username, user);
    }

    public User getUser(String user) {
        return this.storage.get(user);
    }

    public boolean userExists(String user) {
        return this.storage.contains(user);
    }
}
