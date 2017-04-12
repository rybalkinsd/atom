package ru.atom.dao;

import ru.atom.User;
import ru.atom.dao.*;

/**
 * Created by IGIntellectual on 12.04.2017.
 */
public class DatabaseClass
{

    private static UserDao userDao;

    static {
        userDao = new UserDao();
    }

    public static Boolean checkByCondition(String... conditions) {
    return userDao.getAllWhere(conditions)
            .stream()
            .findFirst()
            .isPresent();
}

    public static void insertUser(User user) {
        userDao.insert(user);
    }
}
