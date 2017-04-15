package ru.atom.dbhackaton.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.dbhackaton.model.Token;
import ru.atom.dbhackaton.model.User;

import java.util.List;

/**
 * Created by dmitriy on 12.04.17.
 */
public class TokenDao {

    private static final Logger log = LogManager.getLogger(UserDao.class);

    private static TokenDao instance = new TokenDao();

    public static TokenDao getInstance() {
        return instance;
    }

    private TokenDao() {}

    public List<Token> getAll(Session session) {
        return session.createCriteria(Token.class).list();
    }

    public Token getByStrToken(Session session, String strToken) {
        return (Token) session
                .createQuery("from Token where value = :strToken")
                .setParameter("strToken", strToken)
                .uniqueResult();
    }

    public void insert(Session session, Token token) {
        session.saveOrUpdate(token);
    }

    public void remove(Session session, Token token) {
        session.delete(token);
    }
}
