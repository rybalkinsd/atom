package ru.atom.dbhackaton.server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.dbhackaton.server.model.GameResults;


/**
 * Created by pavel on 15.04.17.
 */
public class GameSessionDao {
    private static final Logger log = LogManager.getLogger(GameSessionDao.class);

    private static final GameSessionDao instance = new GameSessionDao();

    private GameSessionDao() {
    }

    public static GameSessionDao getInstance() {
        return instance;
    }

    public void saveSession(Session session, GameResults results) {
        session.persist(results);
    }
}
