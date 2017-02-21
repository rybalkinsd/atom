package accountserver.authDAO;

import java.util.List;

/**
 * Created by User on 05.11.2016.
 */
public interface AuthDAO<T> {

    List<T> getAll();

    List<T> getAllWhere(String ... hqlCondidtions);

    boolean insert(T t);

    boolean delete(T t);

}
