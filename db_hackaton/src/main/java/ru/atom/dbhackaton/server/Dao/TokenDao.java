package ru.atom.dbhackaton.server.Dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.dbhackaton.server.model.Token;
import ru.atom.dbhackaton.server.model.User;

import java.util.List;

/**
 * Created by ilnur on 17.04.17.
 */
public class TokenDao {
    private static final Logger log = LogManager.getLogger(UserDao.class);

    private static TokenDao instance = new TokenDao();

    public static TokenDao getInstance() {
        return instance;
    }

    private TokenDao() {
    }

    public List<User> getAll(Session session) {
        return session.createCriteria(Token.class).list();
    }

    public void insert(Session session, Token token) {
        session.saveOrUpdate(token);
    }

//    public Token getById(Session session, Integer id) {
//        return (Token) session
//                .createQuery("from Token where uid = :id")
//                .setParameter("uid", id)
//                .uniqueResult();
//    }

    public User getByName(Session session, String name) {
        return (User) session
                .createQuery("from User where login = :name")
                .setParameter("name", name)
                .uniqueResult();
    }
}
