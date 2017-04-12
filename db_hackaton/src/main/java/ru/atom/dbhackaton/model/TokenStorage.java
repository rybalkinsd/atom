package ru.atom.dbhackaton.model;

import org.hibernate.Session;
import ru.atom.dbhackaton.hibernate.LoginEntity;
import ru.atom.dbhackaton.hibernateutil.HibernateUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vladfedorenko on 26.03.17.
 */

public class TokenStorage {
    public static void saveLogin(LoginEntity loginUser){
        Session session = HibernateUtil.getSession();
        session.save(loginUser);
        session.close();
    }
    public static void getByName(String name){
        Session session = HibernateUtil.getSession();
        LoginEntity user = (LoginEntity) session
                .createQuery("from LoginEntity where  = :user")
                .setParameter("user", name)
                .uniqueResult();
        session.close();
    }
}
