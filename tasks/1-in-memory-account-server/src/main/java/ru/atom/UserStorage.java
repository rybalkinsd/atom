package ru.atom;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UserStorage {
    private ConcurrentHashMap<String, String> storage = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, String> getStorage() {
        return storage;
    }

    public boolean validatePassword(String password, String user) {
        return this.getPasswordByName(user).equals(password);
    }

    public String getPasswordByName(String user) {
        for (Map.Entry<String, String> entry : storage.entrySet()) {
            if (entry.getValue().equals(user)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public ArrayList<String> getAll() {
        ArrayList<String> allUsers = new ArrayList<>();
        for (Map.Entry<String, String> entry : storage.entrySet()) {
            allUsers.add(entry.getValue());
        }
        return allUsers;
    }

}
