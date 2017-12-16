package matchmaker.dao;

import matchmaker.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer> {
    User getByName(String name);
}
