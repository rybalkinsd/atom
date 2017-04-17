package ru.atom.dbhackaton.mm;

import org.hibernate.Session;
import ru.atom.dbhackaton.hibernateutil.HibernateUtil;

/**
 * Created by ilysk on 16.04.17.
 */
public class UserGameResultDao {
    public static void saveGameResults(UserGameResult userGameResult) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(userGameResult);
        session.getTransaction().commit();
        session.close();
    }
}
