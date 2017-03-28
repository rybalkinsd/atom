package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by serega on 26.03.17.
 */
public class UsersCache {
    private static ConcurrentLinkedQueue<User> registeredUsers = new ConcurrentLinkedQueue<>();
    private static ConcurrentHashMap<Long, User> loginedUsers = new ConcurrentHashMap<>();
    private static Logger logger = LogManager.getLogger(UsersCache.class);

    public static Long getTokenByUser(User user) {
        for (Map.Entry<Long, User> entry : loginedUsers.entrySet()) {
            if (user.equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        return -1L;
    }

    public static User getUserByToken(Long token) {
        if (token != null) {
            return loginedUsers.get(token);
        }
        return null;
    }

    public static LinkedList<User> getLoginedUsers() {
        LinkedList<User> result = new LinkedList<>();
        for (User user : loginedUsers.values()) {
            result.add(user);
        }
        return result;
    }

    public static LinkedList<User> getRegisteredUsers() {
        LinkedList<User> result = new LinkedList<>();
        for (User user : registeredUsers) {
            result.add(user);
        }
        return result;
    }

    public static boolean registerUser(User user) {
        if (user != null) {
            if (registeredUsers.contains(user)) {
                logger.info("User with name {} already exist", user.getName());
                return false;
            }
            registeredUsers.add(user);
            logger.info("User {} is registered successful", user);
            return true;
        }

        logger.info("Field user is null");
        return false;
    }

    public static Long login(User user) {
        if (user != null) {
            if (registeredUsers.contains(user)) {
                if (loginedUsers.containsValue(user)) {
                    logger.info("User {} is already logined", user.getName());
                    return getTokenByUser(user);
                }

                Long token = Token.createToken();
                loginedUsers.put(token, user);
                logger.info("User {} is logined", user.getName());
                return token;
            }

            logger.info("User {} is not registered", user.getName());
            return -1L;
        }

        logger.info("Field user is null");
        return -1L;
    }

    public static boolean logout(Long token) {
        if (token != null) {
            if (loginedUsers.containsKey(token)) {
                User user = loginedUsers.remove(token);
                logger.info("User {} is logouted", user.getName());
                return true;
            }
            logger.info("Token {} is not authorized", token);
            return false;
        }

        logger.info("Field token is null");
        return false;
    }
}
