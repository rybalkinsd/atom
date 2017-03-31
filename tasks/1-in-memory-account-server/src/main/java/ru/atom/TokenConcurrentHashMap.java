package ru.atom;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ilysk on 29.03.17.
 */
public class TokenConcurrentHashMap<T, U> extends ConcurrentHashMap {

    public ArrayList<U> getAllUsers() {
        ArrayList<U> allUsers = new ArrayList<>();
        for (Object token: this.keySet()) {
            allUsers.add((U) this.get(token));
        }

        return allUsers;
    }
}
