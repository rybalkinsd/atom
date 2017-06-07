package ru.atom.dbhackaton.server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.server.dao.Database;
import ru.atom.dbhackaton.server.dao.GameSessionDao;
import ru.atom.dbhackaton.server.dao.UserDao;
import ru.atom.dbhackaton.server.model.GameResults;
import ru.atom.dbhackaton.server.model.User;

import java.util.List;

/**
 * Created by pavel on 17.04.17.
 */
public class GameSessionService {
    private static final Logger LOGGER = LogManager.getLogger(GameSessionService.class);

    public void addGameToDataBase(List<GameResults> list) {
        Transaction tnx = null;
        try (Session session = Database.session()) {
            tnx = session.beginTransaction();
            for (GameResults game : list) {
                User user = UserDao.getInstance().getByName(session, game.getUser().getName());
                GameSessionDao.getInstance().saveSession(session, game.setUser(user));
            }
            tnx.commit();
        } catch (RuntimeException e) {
            if (tnx != null || tnx.isActive()) {
                tnx.rollback();
                LOGGER.error("Transaction failed!");
            }
        }
    }
}
