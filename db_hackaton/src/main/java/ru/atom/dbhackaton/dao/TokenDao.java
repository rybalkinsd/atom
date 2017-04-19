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
// TODO: 14.04.17   пока не сделано вообще никак
public class TokenDao {
    private static final Logger log = LogManager.getLogger(TokenDao.class);

    private static TokenDao instance = new TokenDao();

    public TokenDao(){}

    public static TokenDao getInstance() {
        return instance;
    }

    public Token getToken(Session session, long token) {
        return (Token) session
                .createQuery("from token where token = :token")
                .setParameter("token", token)
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
