package ru.atom.dao;

import ru.atom.object.Token;
import ru.atom.object.User;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by IGIntellectual on 12.04.2017.
 */
public class DatabaseClass
{

    private  UserDao userDao = new UserDao();
    private  TokenDao tokenDao = new TokenDao();




    public  Boolean checkByConditionUser(String... conditions) {
    return userDao.getAllWhere(conditions)
            .stream()
            .findFirst()
            .isPresent();
}

    public  void insertUser(User user) {
        userDao.insert(user);
    }

    private  Token getToken(User user) {
       return null;
    }

    public Token issueToken(String name) {
        return null;
    }
}
