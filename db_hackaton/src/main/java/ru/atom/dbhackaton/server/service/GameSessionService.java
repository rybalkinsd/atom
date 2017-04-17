package ru.atom.dbhackaton.server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.server.Dao.Database;
import ru.atom.dbhackaton.server.Dao.GameSessionDao;
import ru.atom.dbhackaton.server.model.GameSession;

/**
 * Created by pavel on 17.04.17.
 */
public class GameSessionService {
    private static final Logger LOGGER = LogManager.getLogger(GameSessionService.class);

    public void addGameToDataBase(GameSession gameSession) {
        Transaction tnx = null;
        try (Session session = Database.session()){
            tnx = session.beginTransaction();
            GameSessionDao.getInstance().saveSession(session,gameSession);
            tnx.commit();
        } catch (RuntimeException e) {
            if (tnx != null || tnx.isActive()) {
                tnx.rollback();
                LOGGER.error("Transaction failed!");
            }
        }
    }
}
