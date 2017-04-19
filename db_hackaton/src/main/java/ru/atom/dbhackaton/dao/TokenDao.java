package ru.atom.dbhackaton.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.dbhackaton.resource.Token;
import ru.atom.dbhackaton.resource.User;

import java.util.List;

/**
 * Created by BBPax on 13.04.17.
 */
public class TokenDao {
    private static final Logger log = LogManager.getLogger(TokenDao.class);

    private static TokenDao instance = new TokenDao();

    private TokenDao(){}

    public static TokenDao getInstance() {
        return instance;
    }

    public Token getToken(Session session, long token) {
        return (Token) session
                .createQuery("from token where token = :token")
                .setParameter("token", token)
                .uniqueResult();
    }

    public Token getByUser(Session session, User user) {
        return (Token) session
                .createQuery("from token where user_id = :user_id")
                .setParameter("user_id", user.getId())
                .uniqueResult();
    }

    public User getUserByToken(Session session, long token) {
        return getToken(session, token).getUser();
    }

    public List<Token> getAll(Session session) {
        return session.createCriteria(Token.class).list();
    }

    public void insert(Session session, Token token) {
        session.saveOrUpdate(token);
    }

    public void delete(Session session, Token token) {
        session.saveOrUpdate(token.setUser(null));
        session.delete(token);
    }
}
