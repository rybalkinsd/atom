package dao;

import model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer> {

    User findByLogin(String login);

    int removeByLogin(String login);

    User findPasshashByLogin(String login);
}
