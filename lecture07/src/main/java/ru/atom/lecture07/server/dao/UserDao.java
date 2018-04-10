package ru.atom.lecture07.server.dao;

import ru.atom.lecture07.server.model.OnlineState;
import ru.atom.lecture07.server.model.User;

import java.util.List;

public interface UserDao {
    /**
     * Return the user having the passed login or null if no user is found.
     *
     * @param login the user login.
     */
    User getByLogin(String login);

    void saveUser(User user);

    void saveState(OnlineState state);

    OnlineState getStateByLogin(String login);

    List<User> findAll();

    void delete(User user);

    void leave(OnlineState state);
}
