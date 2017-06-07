package ru.atom.dbhackaton.server.dao;

/**
 * Created by alex on 20.04.17.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.dbhackaton.server.model.UserResult;

public class UserResultDao {
    private static final Logger log = LogManager.getLogger(UserResultDao.class);

    private static UserResultDao instance = new UserResultDao();

    public static UserResultDao getInstance() {
        return instance;
    }

    private UserResultDao() {
    }

    public void insert(Session session, UserResult result) {
        session.saveOrUpdate(result);
        log.info("New User Result : {}", result);
    }


}