package ru.atom.dbhackaton.server.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDao {
    private static final Logger log = LogManager.getLogger(UserDao.class);

    private static UserDao instance = new UserDao();

    public static UserDao getInstance() {
        return instance;
    }

    private UserDao(){}

    public List<User> getAll(Session session) {
        return session.createCriteria(User.class).list();
    }

    public void insert(Session session, User user) {
        session.saveOrUpdate(user);
    }

    public void insertTxn(Session session, User user) {
        Transaction txn = session.beginTransaction();
        insert(session, user);
        txn.commit();
    }

    public void deleteByName(Session session, String name) {
        session.createQuery("delete User where login = :name")
                .setParameter("name", name)
                .executeUpdate();
    }

    public void deleteByNameTxn(Session session, String name) {
        Transaction txn = session.beginTransaction();
        deleteByName(session, name);
        txn.commit();
    }

    public User getByName(Session session, String name) {
        return (User) session
                .createQuery("from User where login = :name")
                .setParameter("name", name)
                .uniqueResult();
    }

    public User getByToken(Session session, long token) {
        return (User) session
                .createQuery("from User where token = :token")
                .setParameter("token", token)
                .uniqueResult();
    }
}
