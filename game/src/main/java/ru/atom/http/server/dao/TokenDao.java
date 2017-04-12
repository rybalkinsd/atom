package ru.atom.http.server.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.http.server.model.Token;
import ru.atom.http.server.model.User;

public class TokenDao {
    private static final Logger log = LogManager.getLogger(TokenDao.class);

    private static TokenDao instance = new TokenDao();

    public static TokenDao getInstance() {
        return instance;
    }

    private TokenDao(){}

    public void insert(Session session, Token token) {
        session.saveOrUpdate(token);
    }

    public Token getByToken(Session session, String token) {
        return (Token) session
                .createQuery("from Token where token = :token")
                .setParameter("token", token)
                .uniqueResult();
    }

    public Token getByUserName(Session session, String name) {
        return (Token) session
                .createQuery("from Token t where t.user.name = :name")
                .setParameter("name", name)
                .uniqueResult();
    }

    /*public boolean validToken(Session session, String token) {
        Token token = getByToken(session, token);
        return token != null;
    }*/
}
