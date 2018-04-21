package ru.atom.chat.dao;

import ru.atom.chat.user.User;

import java.util.List;

public interface UserDao {
    /**
     * Return the user having the passed login or null if no user is found.
     *
     * @param login the user login.
     */
    User getByLogin(String login);

    // List<User> getByActive(Boolean login);

    void save(User user);

    void update(User user);

    void delete(User user);

    List<User> findAll();
}
