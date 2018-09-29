package ru.atom.lecture08.websocket.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.atom.lecture08.websocket.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {
    /**
     * Return the user having the passed login or null if no user is found.
     *
     * @param login the user login.
     */
    User getByLogin(String login);
}