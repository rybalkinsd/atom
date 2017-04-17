package ru.atom.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.object.Token;
import ru.atom.object.User;
import ru.atom.dao.*;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by IGIntellectual on 12.04.2017.
 */
public class DatabaseClass
{
    private static final Logger log = LogManager.getLogger(DatabaseClass.class);

    private static UserDao userDao;
    private static TokenDao tokenDao;

    static {
        userDao = new UserDao();
        tokenDao = new TokenDao();
    }

    public static Boolean checkByConditionUser (String... conditions) {
    return userDao.getAllWhere(conditions)
            .stream()
            .findFirst()
            .isPresent();
}

    public static Boolean checkByConditionToken (String... conditions) {
        return tokenDao.getAllWhere(conditions)
                .stream()
                .findFirst()
                .isPresent();
    }

    public static void insertUser(User user) {
        userDao.insert(user);
    }

    private static Token getToken(String tokenValue) {
        String findByValueCondition = "value = \'" + tokenValue + "\';";
        log.info("get token");
        List<Token> alreadyWithToken = tokenDao.getAllWhere(findByValueCondition);
        log.info("1");
        Token token = alreadyWithToken.stream().findFirst().get();
        log.info("2");
        return token;
    }

    public static boolean deleteToken(String tokenValue){
       return tokenDao.delete(getToken(tokenValue));
    }

    public static Token issueToken(String login) {
        List<User> alreadylogined = userDao.getAllWhere("login = \'" + login + "\'");
        User userForToken = alreadylogined.stream().findFirst().get();
        final String findByIduserCondition = "iduser=\'" + userForToken.getIdUser() + "\'";
        Token yourToken;
        if(checkByConditionToken(findByIduserCondition)){
            List<Token> alreadyWithToken = tokenDao.getAllWhere(findByIduserCondition);
            yourToken = alreadyWithToken.stream().findFirst().get();
        }else {
            yourToken = new Token().setUser(userForToken);
            tokenDao.insert(yourToken);
        }
        return yourToken;
    }
}
