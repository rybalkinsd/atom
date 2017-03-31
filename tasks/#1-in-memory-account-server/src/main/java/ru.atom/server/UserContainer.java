package ru.atom.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by konstantin on 25.03.17.
 */
public class UserContainer {
    private static Logger logger = LogManager.getLogger(UserContainer.class);
    private static ConcurrentLinkedQueue<User> registeredUsers = new ConcurrentLinkedQueue<>();
    private static ConcurrentHashMap<Long, User> logginedUsers = new ConcurrentHashMap<>();

    public static Long getTockenByUser(User user) {
        for (Map.Entry<Long, User> entry : logginedUsers.entrySet()) {
            if (user.equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        return -1L;
    }

    public static LinkedList<User> getLogginedUsers() {
        LinkedList<User> result = new LinkedList<>();
        for (User user : logginedUsers.values()) {
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

    public static boolean registerUser(User user) throws NullPointerException {
        if (user.getName() != null && user.getPassword() != null) {
            if (registeredUsers.contains(user)) {
                logger.info("User with name {} already exist", user.getName());
                return false;
            }
            registeredUsers.add(user);
            logger.info("User {} is registered successful", user);
            return true;
        }
        throw new NullPointerException();
    }

    public static Long login(User user) throws NullPointerException {
        if (user.getName() != null && user.getPassword() != null) {
            if (registeredUsers.contains(user)) {
                if (logginedUsers.containsValue(user)) {
                    logger.info("User {} is loggined", user.getName());
                    return getTockenByUser(user);
                }

                Long newTocken = Tocken.generateTocken();
                logginedUsers.put(newTocken, user);
                logger.info("User {} is loggined", user.getName());
                return newTocken;
            }

            logger.info("User {} is not registered", user.getName());
            return -1L;
        }

        throw new NullPointerException();
    }

    public static boolean logout(Long tocken) throws NullPointerException {
        if (tocken != null) {
            if (logginedUsers.containsKey(tocken)) {
                User user = logginedUsers.remove(tocken);
                logger.info("User {} is loggined out", user.getName());
                return true;
            }
            logger.info("Tocken {} is not authorized", tocken);
            return false;
        }

        throw new NullPointerException();
    }
}