package leaderboard.dao;

import accountserver.dao.exceptions.DaoException;

import java.sql.ResultSet;

/**
 * Created by eugene on 12/16/16.
 */
public interface ResultSetExecutor<T> {
    T execute(ResultSet in) throws DaoException;
}
