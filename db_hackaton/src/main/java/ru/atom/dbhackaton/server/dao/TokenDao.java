package ru.atom.dbhackaton.server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.dbhackaton.server.model.Token;

public class TokenDao {
    private static final Logger log = LogManager.getLogger(TokenDao.class);

    private static final TokenDao instance = new TokenDao();

    private TokenDao() {

    }

    public static TokenDao getInstance() {
        return instance;
    }

    public void login(Session session, Token token) {
        session.persist(token);
    }

    public void logout(Session session, long token) {
        session.createQuery("delete Token where token = :token")
                .setParameter("token", token).executeUpdate();
    }

    public Token getTokenByUserName(Session session, String name) {
        return (Token) session.createQuery("from Token where user.name = :name")
                .setParameter("name", name)
                .uniqueResult();
    }

    public Token getToken(Session session, long token) {
        return (Token) session.createQuery("from Token where token = :token")
                .setParameter("token", token)
                .uniqueResult();
    }


}
