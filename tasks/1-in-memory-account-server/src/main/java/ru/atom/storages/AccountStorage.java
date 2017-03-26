package ru.atom.storages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.base.User;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mkai on 3/26/17.
 */
public class AccountStorage {
    private static final Logger logger = LogManager.getLogger(AccountStorage.class);

    private ConcurrentHashMap<String, User> accounts = new ConcurrentHashMap<>();

    public boolean addAccount(String userName, String password) {
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

    public boolean isUserExist(String userName) {
        return accounts.containsKey(userName);
    }

    public User getUser(String name) {
        return accounts.get(name);
    }


}
