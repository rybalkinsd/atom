package ru.atom.storages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.base.User;

import java.util.concurrent.ConcurrentHashMap;


public class AccountStorage {
    private static final Logger logger = LogManager.getLogger(AccountStorage.class);

    private static ConcurrentHashMap<String, User> accounts = new ConcurrentHashMap<>();

    public static boolean addAccount(String userName, String password) {
        if (isUserExist(userName)) {
            logger.info("Регистрация невозможна: пользователь уже зарегистрирован.");
            return false;
        }
        try {
            final User user = new User(userName, password);
            accounts.put(userName, user);
        } catch (IllegalStateException exception) {
            logger.error(exception.getMessage());
            return false;
        }
        logger.info("Пользователь {} успешно зарегистрирован", userName);
        return true;
    }

    public static boolean isUserExist(String userName) {
        return accounts.containsKey(userName);
    }

    public static User getUser(String name) {
        return accounts.get(name);
    }


}
