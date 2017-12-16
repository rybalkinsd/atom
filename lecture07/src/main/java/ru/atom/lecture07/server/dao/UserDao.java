package ru.atom.lecture07.server.dao;

import org.springframework.data.repository.CrudRepository;
import ru.atom.lecture07.server.model.User;

public interface UserDao extends CrudRepository<User, Integer> {
    /**
     * Return the user having the passed login or null if no user is found.
     *
     * @param login the user login.
     */
    User getByLogin(String login);
}
