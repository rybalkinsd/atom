package ru.atom;

import ru.atom.User;

import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Fella on 27.03.2017.
 */
public class Users {
    private static ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public static void put(User user) {
        users.put(user.getLogin(), user);
    }

    public static boolean isContainsName(String login) {
        return users.containsKey(login);
    }

    public static String getUserPsword(String login) {
        return users.get(login).getPassword();
    }

    public static User getUser(String login) {
        return users.get(login);
    }


}
