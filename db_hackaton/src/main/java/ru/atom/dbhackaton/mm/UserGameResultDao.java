package ru.atom.dbhackaton.mm;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import ru.atom.dbhackaton.hibernateutil.HibernateUtil;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by ilysk on 16.04.17.
 */
public class UserGameResultDao {
    public static void saveGameResults(UserGameResult userGameResult) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.save(userGameResult);
            session.getTransaction().commit();
        } catch (PersistenceException ex) {
            throw new PersistenceException();
        } catch (IllegalStateException e) {
            throw new IllegalStateException();
        }
    }

    public static List<UserGameResult> getByGameId(Integer gameId) {
        Session session = HibernateUtil.getSession();
        List<UserGameResult> results = session.createQuery("From UserGameResult where gameID =:gameId")
                .setParameter("gameId", gameId)
                .list();
        session.close();
        if (results.size() == 0) {
            return null;
        }
        return results;
    }

}
