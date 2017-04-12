package ru.atom.dbhackaton.model;

import org.hibernate.Session;
import ru.atom.dbhackaton.hibernate.RegistredEntity;
import ru.atom.dbhackaton.hibernateutil.HibernateUtil;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vladfedorenko on 26.03.17.
 */
public class UserStorage {
    public static void insert(RegistredEntity user) {
        Session session = HibernateUtil.getSession();
        session.saveOrUpdate(user);
        session.close();
    }

    public static RegistredEntity getByName(String user) {
        Session session = HibernateUtil.getSession();
        RegistredEntity newUser = (RegistredEntity) session
                .createQuery("from RegistredEntity where login = :user")
                .setParameter("user", user)
                .uniqueResult();
        session.close();
        return newUser;
    }

    public static boolean userExists(RegistredEntity user) {
        Session session = HibernateUtil.getSession();
        org.hibernate.Query query = session.createQuery("from RegistredEntity where login = :user");
        query.setParameter("user", user);
        boolean result = (query.uniqueResult() != null);
        session.close();
        return result;
    }

    public static void dropUser(RegistredEntity userToDelete) {
        Session session = HibernateUtil.getSession();
        session.delete(userToDelete);
        session.close();
    }
}
