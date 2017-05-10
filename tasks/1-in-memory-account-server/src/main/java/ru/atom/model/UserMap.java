package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class UserMap {
    private static final Logger log = LogManager.getLogger(TokenMap.class);

    private static ConcurrentHashMap<User, String> users;


    public UserMap() {
        this.users = new ConcurrentHashMap<>();
    }

    public boolean putUser(User user, String password) {
        if (!users.containsKey(user)) {
            users.put(user, password);
            return true;
        }
        return false;
    }

    public String getPassword(User user) {
        return users.get(user);
    }

    public User removeUser(User removedUser) {
        users.remove(removedUser);
        return removedUser;
    }

    public boolean authenticate(User user, String password) throws Exception {
        return password.equals(users.get(user));
    }
}