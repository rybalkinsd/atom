package accountserver.dao;

import accountserver.dao.exceptions.DaoException;

import java.util.List;

/**
 * Created by eugene on 10/18/16.
 */
public interface DAO<T> {
    Long insert(T in) throws DaoException;
    T getById(Long id) throws DaoException;
    List<T> getWhere(String ... conditions) throws DaoException;
    List<T> getAll() throws DaoException;

    void remove(T in) throws DaoException;
    void remove(Long id) throws DaoException;

}
