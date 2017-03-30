package ru.atom.server;


import org.eclipse.jetty.util.ConcurrentArrayQueue;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ксения on 25.03.2017.
 */
public class UserStorage {

    private static ConcurrentHashMap<String, User> map = new ConcurrentHashMap<>();

    public static void offer(User user) {
        map.put(user.getName(),user);
    }

    public static User get(String name) {
        return map.get(name);
    }

    public static boolean isLogined(String name) {
        if (map.containsKey(name)) {
            if (map.get(name).getToken() != null) return true;
        }
        return false;
    }

    public static String toJson() {
        String json = "{ \"users\" : [";
        for (Map.Entry<String, User> user:map.entrySet()) {
            if (user.getValue().getToken() != null) json = json.concat("{" + user + "}, ");
        }
        if (!map.isEmpty()) json = json.substring(0, json.length() - 2);
        json = json.concat("]}");
        return json;
    }

    public static boolean isRegistered(String name) {
        if (map.containsKey(name)) return true;
        return false;
    }

}
