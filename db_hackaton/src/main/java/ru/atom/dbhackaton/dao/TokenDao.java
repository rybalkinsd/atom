/*
package ru.atom.dbhackaton.dao;

*/
/**
 * Created by Ксения on 12.04.2017.
 *//*

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.dbhackaton.Token;
import ru.atom.dbhackaton.User;

import java.util.List;

public class TokenDao {
    private static final Logger log = LogManager.getLogger(TokenDao.class);

    private static TokenDao instance = new TokenDao();

    public static TokenDao getInstance() {
        return instance;
    }

    private TokenDao(){}

    public List<User> getAll(Session session) {
        return session.createCriteria(Token.class).list();
    }

    public void insert(Session session, Token token) {
        session.saveOrUpdate(token);
    }

    public User getUserIdByToken(Session session, Long token) {
        return (Integer) session
                .createQuery("from registered_users where token = :token")
                .setParameter("name", name)
                .uniqueResult();
    }
}
*/
