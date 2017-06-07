package ru.atom.dbhackaton.model;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import ru.atom.dbhackaton.hibernate.RegistredEntity;
import ru.atom.dbhackaton.hibernateutil.HibernateUtil;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vladfedorenko on 26.03.17.
 */
public class UserStorage {
    public static void insert(RegistredEntity user) throws ConstraintViolationException {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        } catch (PersistenceException ex) {
            throw new PersistenceException();
        }
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

    public static RegistredEntity getById(Integer userId) {
        Session session = HibernateUtil.getSession();
        RegistredEntity newUser = (RegistredEntity) session
                .createQuery("from RegistredEntity where userId = :user")
                .setParameter("user", userId)
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
        session.beginTransaction();
        session.delete(userToDelete);
        session.getTransaction().commit();
        session.close();
    }
}
