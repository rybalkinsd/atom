package ru.atom.model.dao;

import java.util.List;
import java.util.Optional;

/**
 * Created by s.rybalkin on 17.10.2016.
 */
public interface Dao<T> {
    /**
     * SELECT * from ...
     * @return
     */
    List<T> getAll();

    /**
     * SELECT * ... WHERE cond0 AND ... AND condN
     * @param conditions
     * @return
     */
    List<T> getAllWhere(String ... conditions);

    /**
     * INSERT INTO ...
     * @param t
     */
    void insert(T t);
    Optional<T> findById(int id);
}
