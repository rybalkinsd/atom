package ru.atom.dbhackaton.server.storages;


import ru.atom.dbhackaton.server.base.Token;
import ru.atom.dbhackaton.server.base.User;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


public class TokenStorage {
    private static ConcurrentHashMap<Token, User> tokensMap = new ConcurrentHashMap<>();

    public static ArrayList<String> getOnlineUsers() {
        ArrayList<String> names = new ArrayList<>();
        for (User user : tokensMap.values()) {
            names.add(user.getName());
        }
        return names;
    }
}
