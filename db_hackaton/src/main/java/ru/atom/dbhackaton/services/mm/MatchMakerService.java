package ru.atom.dbhackaton.services.mm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.dao.Database;
import ru.atom.dbhackaton.dao.ResultDao;
import ru.atom.dbhackaton.dao.TokenDao;
import ru.atom.dbhackaton.dao.UserDao;
import ru.atom.dbhackaton.resource.Result;
import ru.atom.dbhackaton.resource.User;

/**
 * Created by BBPax on 18.04.17.
 */
public class MatchMakerService {
    private static final Logger log = LogManager.getLogger(MatchMakerService.class);

    public User findUser(long token) {
        Transaction txn = null;
        User user = new User();
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            user = TokenDao.getInstance().getUserByToken(session, token);
            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
        return user;
    }

    public User findUser(String login) {
        Transaction txn = null;
        User user = new User();
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            user = UserDao.getInstance().getByName(session, login);
            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
        return user;
    }

    public void saveResult(Integer gameId, User user, Integer score) {
        Transaction txn = null;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            Result result = new Result().setGameId(gameId).setUser(user).setScore(score);
            ResultDao.getInstance().insert(session, result);
            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
        log.info("result with gameId = {}, user = {}, score = {} was saved to DB",
                gameId, user.getLogin(), score);
    }
}
