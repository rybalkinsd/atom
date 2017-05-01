package ru.atom.bombergirl.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.bombergirl.dbmodel.Result;

import java.util.List;

/**
 * Created by ikozin on 17.04.17.
 */
public class ResultDao {

    private static final Logger log = LogManager.getLogger(ResultDao.class);

    private static ResultDao instance = new ResultDao();

    public static ResultDao getInstance() {
        return instance;
    }

    private ResultDao() { }

    public List<Result> getAll(Session session) {
        return session.createCriteria(Result.class).list();
    }

    public void insert(Session session, Result result) {
        session.saveOrUpdate(result);
    }

    public void clean(Session session) {
        List<Result> results = session.createQuery("from Result").getResultList();
        for (Result result: results) {
            session.delete(result);
        }
        if (results.size() > 0) {
            session.flush();
        }
    }

    public List<Result> getByGameId(Session session, Integer gameId) {
        return session
                .createQuery("from Result where game_id = :gameId")
                .setParameter("gameId", gameId)
                .getResultList();
    }
}
