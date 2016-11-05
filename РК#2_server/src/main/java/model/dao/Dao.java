package model.dao;

import java.util.List;
import java.util.Optional;

/**
 * Created by venik on 03.11.16.
 */
public interface Dao<T> {
    /**
     * SELECT * from ...
     * @return
     */
    List<T> getAll();

    /**
     * SELECT * ... WHERE cond0 AND ... AND condN
     * @param hqlCondidtions
     * @return
     */
    List<T> getAllWhere(String... hqlCondidtions);

    /**
     * INSERT INTO ...
     * @param t
     */
    void insert(T t);

    /**
     * SELECT * from ... WHERE id=
     * @param id
     * @return Optional.empty() if nothing found
     */
    default Optional<T> findById(int id) {
        return Optional.ofNullable(
                getAllWhere("id=" + id).get(0)
        );
    }

}
