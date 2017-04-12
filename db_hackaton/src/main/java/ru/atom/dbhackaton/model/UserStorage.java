package ru.atom.dbhackaton.model;

import org.hibernate.Session;
import ru.atom.dbhackaton.hibernate.RegistredEntity;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vladfedorenko on 26.03.17.
 */
public class UserStorage {
    private static ConcurrentHashMap<String, RegistredEntity> storage = new ConcurrentHashMap();

    public void insert(Session session, RegistredEntity user) {
        session.saveOrUpdate(user);
    }

    public static User getByName(Session session, RegistredEntity user) {
        return (User) session
                .createQuery("from RegistredEntity where login = :user")
                .setParameter("name", user)
                .uniqueResult();
    }

    public boolean userExists(Session session, RegistredEntity user) {
        org.hibernate.Query query = session.createQuery("from users where username = :user");
        query.setParameter("username", user);
        return query.uniqueResult() != null;
    }

    public void dropUser(Session session, RegistredEntity userToDelete) {
        session.delete(userToDelete);
    }
}
