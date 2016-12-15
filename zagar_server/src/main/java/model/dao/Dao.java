package model.dao;

import java.util.List;
import java.util.Optional;

/**
 * Created by s on 10.11.16.
 */
public interface Dao<T> {
    /**
     * SELECT * from ...
     * @return
     */
    List<T> getAll();

    /**
     * SELECT * ... WHERE cond0 AND ... AND condN
     * @param sqlCondidtions
     * @return
     */
    List<T> getAllWhere(String... sqlCondidtions);

    /**
     * INSERT INTO ...
     * @param t
     */
    void insert(T t);

    void clear();

    void remove(T t);

    void update(T t);
    /**
     * SELECT * from ... WHERE id=
     * @param id
     * @return Optional.empty() if nothing found
     */
    Optional<T> findById(Long id);
}
