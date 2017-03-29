package ru.atom.server;


import org.eclipse.jetty.util.ConcurrentArrayQueue;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ксения on 25.03.2017.
 */
public class LoginedUserStorage {

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

//    public static String toJson() {
//        boolean hasLogined = false;
//        String json = "{ \"users\" : [";
//        for (User user : map) {
//            if (!(user.getToken() == (null))) {
//                json = json.concat("{" + user + "}, ");
//                hasLogined = true;
//            }
//        }
//        if (hasLogined) json = json.substring(0, json.length() - 2);
//        json = json.concat("]}");
//        return json;
//    }



    public static boolean isRegistered(String name) {
        if (map.containsKey(name)) return true;
        return false;
    }
    public static Long getLong(String name) {
        return map.get(name).getToken();
    }
}
