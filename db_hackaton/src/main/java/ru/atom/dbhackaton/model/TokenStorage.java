package ru.atom.dbhackaton.model;

import org.hibernate.Session;
import ru.atom.dbhackaton.hibernate.LoginEntity;
import ru.atom.dbhackaton.hibernateutil.HibernateUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import static ru.atom.dbhackaton.model.UserStorage.getByName;

/**
 * Created by vladfedorenko on 26.03.17.
 */

public class TokenStorage {
    public static void saveLogin(LoginEntity loginUser){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(loginUser);
        session.getTransaction().commit();
        session.close();
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
        session.delete(login);
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
