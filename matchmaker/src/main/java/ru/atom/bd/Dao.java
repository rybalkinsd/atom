package ru.atom.bd;

import java.util.List;
import java.util.Optional;

/**
 * Created by s.rybalkin on 17.10.2016.
 */
public interface Dao<T> {
    /**
     * SELECT * from ...
     */
    List<T> getAll();

    /**
     * SELECT * ... WHERE cond0 AND ... AND condN
     */
    List<T> getAllWhere(String... conditions);

    /**
     * INSERT INTO ...
     */
    void insert(T t);

    /**
     * SELECT * from ... WHERE id=
     *
     * @return Optional.empty() if nothing found
     */
    default Optional<T> findById(int id) {
        return Optional.ofNullable(
                getAllWhere("id=" + id).get(0)
        );
    }
}