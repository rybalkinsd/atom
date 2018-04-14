package ru.atom.lecture08.websocket.dao;

import ru.atom.lecture08.websocket.model.User;

import java.util.List;

public interface UserDao {
    /**
     * Return the user having the passed login or null if no user is found.
     *
     * @param login the user login.
     */
    User getByLogin(String login);

    void save(User user);

    List<User> findAll();

    String getColorByLogin(String login);

    void delete(String login);
}
