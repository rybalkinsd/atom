package ru.atom.server;


import org.eclipse.jetty.util.ConcurrentArrayQueue;

/**
 * Created by Ксения on 25.03.2017.
 */
public class LoginedUserStorage {

    private static ConcurrentArrayQueue<User> map = new ConcurrentArrayQueue<>();

    public static void offer(User user) {
        map.offer(user);
    }

    public static User getUserByName(String name) {
        for (User user:map) {
            if (user.getName().equals(name)) return user;
        }
        return null;
    }

    public static User getUserById(Integer id) {
        if (id == null) return null;
        for (User user:map) {
            if (user.getId().equals(id)) return user;
        }
        return null;
    }

    public static boolean containsUser(Integer id) {
        if (id == null) return  false;
        if (!map.isEmpty()) {
            for (User user : map) {
                if (user.getId().equals(id)) return true;
            }
        }
        return false;
    }

    public static boolean isLogined(Integer id) {
        if (containsUser(id)) {
            if (!(getUserById(id).getToken() == null)) return true;
        }
        return false;
    }

    public static String toJson() {
        boolean hasLogined = false;
        String json = "{ \"users\" : [";
        for (User user : map) {
            if (!(user.getToken() == (null))) {
                json = json.concat("{" + user + "}, ");
                hasLogined = true;
            }
        }
        if (hasLogined) json = json.substring(0, json.length() - 2);
        json = json.concat("]}");
        return json;
    }
}
