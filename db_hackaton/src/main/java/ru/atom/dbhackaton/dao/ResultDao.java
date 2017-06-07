package ru.atom.dbhackaton.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ru.atom.dbhackaton.resource.Result;
import ru.atom.dbhackaton.resource.User;

import java.util.List;

/**
 * Created by BBPax on 18.04.17.
 */
public class ResultDao {
    private static final Logger log = LogManager.getLogger(ResultDao.class);

    private static ResultDao instance = new ResultDao();

    private ResultDao(){}

    public static ResultDao getInstance() {
        return instance;
    }

    public List<Result> getAll(Session session) {
        return session.createCriteria(Result.class).list();
    }

    public void insert(Session session, Result result) {
        session.saveOrUpdate(result);
    }
}
