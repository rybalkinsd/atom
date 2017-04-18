package ru.atom.dbhackaton.model;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import ru.atom.dbhackaton.hibernate.LoginEntity;
import ru.atom.dbhackaton.hibernateutil.HibernateUtil;

import javax.persistence.PersistenceException;


import java.sql.SQLException;

import static ru.atom.dbhackaton.model.UserStorage.getByName;

/**
 * Created by vladfedorenko on 26.03.17.
 */

public class TokenStorage {
    public static void saveLogin(LoginEntity loginUser){
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.save(loginUser);
            session.getTransaction().commit();
        } catch (PersistenceException ex) {
            throw new PersistenceException();
        }
    }
    public static LoginEntity getLoginByName(String name){
        Session session = HibernateUtil.getSession();
        LoginEntity user = (LoginEntity) session
                .createQuery("from LoginEntity where id = :user")
                .setParameter("user", getByName(name).getUserId())
                .uniqueResult();
        session.close();
        return user;
    }
    public static void logoutToken(String name){
        Session session = HibernateUtil.getSession();
        LoginEntity login = getLoginByName(name);
        session.beginTransaction();
        session.delete(login);
        session.getTransaction().commit();
        session.close();
    }
    public static LoginEntity getByToken(Long token){
        Session session = HibernateUtil.getSession();
        String tokenStr = Long.toString(token);
        LoginEntity user = (LoginEntity) session
                .createQuery("from LoginEntity where token = :token")
                .setParameter("token", tokenStr)
                .uniqueResult();
        session.close();
        return user;

    }
}
