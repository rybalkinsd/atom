package ru.atom.server;

import org.eclipse.jetty.util.ConcurrentArrayQueue;

/**
 * Created by Ксения on 25.03.2017.
 */
public class RegistratedUserStorage {

    private static ConcurrentArrayQueue<User> map = new ConcurrentArrayQueue<>();

    public static void offer(User user) {
        map.offer(user);
    }

    public static User getUserByName(String name) {
        if (!map.isEmpty()) {
            for (User user : map) {
                if (user.getName().equals(name)) return user;
            }
        }
        return null;
    }

    public static User getUserById(Integer id) {
        if (!map.isEmpty()) {
            for (User user:map) {
                if (user.getId().equals(id)) return user;
            }
        }
        return null;
    }

    public static boolean containsUser(String name) {
        if (!map.isEmpty()) {
            for (User user : map) {
                if (user.getName().equals(name)) return true;
            }
        }
        return false;
    }

}
