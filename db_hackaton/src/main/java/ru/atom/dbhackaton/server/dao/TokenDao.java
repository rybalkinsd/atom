package ru.atom.dbhackaton.server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.dbhackaton.server.model.Token;

import java.util.List;

/**
 * Created by Angela on 18.04.2017.
 */
public class TokenDao {
    private static final Logger log = LogManager.getLogger(UserDao.class);

    private static TokenDao instance = new TokenDao();

    public static TokenDao getInstance() {
        return instance;
    }

    private TokenDao() {
    }

    public List<Token> getAll(Session session) {
        return session.createCriteria(Token.class).list();
    }

    public void insert(Session session, Token token) {
        session.saveOrUpdate(token);
    }

    public void remove(Session session, Token token) {
        session.delete(token);
    }

    public Token getByToken(Session session, String stringToken) {
        return (Token) session
                .createQuery("from Token where token = :stringToken")
                .setParameter("stringToken", stringToken)
                .uniqueResult();
    }

    public Token getByUsername(Session session, String name) {
        return (Token) session
                .createQuery("from Token where username = :name")
                .setParameter("name", name)
                .uniqueResult();
    }


}
