package ru.atom.dbhackaton.model;

import org.hibernate.Session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vladfedorenko on 26.03.17.
 */
public class UserStorage {
    private static ConcurrentHashMap<String, User> storage = new ConcurrentHashMap();

    public void insert(Session session, User user) {
        session.saveOrUpdate(user);
    }

    public static User getByName(Session session, User user) {
        return (User) session
                .createQuery("from User where login = :user")
                .setParameter("name", user)
                .uniqueResult();
    }

    public boolean userExists(Session session, String user) {
        org.hibernate.Query query = session.createQuery("from users where username = :user");
        query.setParameter("username", user);
        return query.uniqueResult() != null;
    }

    public void dropUser(Session session, User userToDelete) {
        session.delete(userToDelete);
    }
}
