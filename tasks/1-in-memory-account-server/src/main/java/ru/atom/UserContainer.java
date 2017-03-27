package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by pavel on 23.03.17.
 */
public class UserContainer {
    private static ConcurrentLinkedQueue<User> registeredUsers = new ConcurrentLinkedQueue<>();
    private static ConcurrentHashMap<Long, User> logginedUsers = new ConcurrentHashMap<>();
    private static Logger logger = LogManager.getLogger(UserContainer.class);


    /**
     * Return tocken by user or -1 if user didn't find
     *
     * @param user - user in container
     */
    public static Long getTockenByUser(User user) {
        for (Map.Entry<Long, User> entry : logginedUsers.entrySet()) {
            if (user.equals(entry.getValue())) {
                return entry.getKey();
            }
        }

        return -1L;
    }

    /**
     * Return user in container by tocken or ({@code null}) if user
     * doesn't fined.
     *
     * @param tocken - tocken of user
     */
    public static User getUserByTocken(Long tocken) {
        if (tocken != null) {
            return logginedUsers.get(tocken);
        }
        return null;
    }

    /**
     * Return the collection of loggined users
     */
    public static Collection<User> getLogginedUsers() {
        return logginedUsers.values();
    }

    /**
     * Return the collection of registered users
     */
    public static Collection<User> getRegisteredUsers() {
        return registeredUsers;
    }


    /**
     * Register user in AuthService
     *
     * @param user - adding user
     * @return {@code true} if successful, {@code false} if user exist
     * in container.
     */
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

    /**
     * Login user in AuthService
     *
     * @param user - user for login
     * @return tocken of user if login is successful or -1 if login is failed
     */
    public static Long login(User user) {
        if (user != null) {
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

        logger.info("Field user is null");
        return -1L;
    }

    /**
     * Remove user from the container by tocken
     *
     * @param tocken - tocken of user.
     * @return true if user removed, false if user didn't remove.
     */
    public static boolean logout(Long tocken) {
        if (tocken != null) {
            if (logginedUsers.containsKey(tocken)) {
                User deleted = logginedUsers.remove(tocken);
                logger.info("User {} is loggined out", deleted.getName());
                return true;
            }
            logger.info("Tocken {} is not authorized", tocken);
            return false;
        }

        logger.info("Field tocken is null");
        return false;
    }
}
