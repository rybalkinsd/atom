package ru.atom.chat.dao;

import ru.atom.chat.user.Player;

import java.util.List;

public interface PlayerDao {
    /**
     * Return the user having the passed login or null if no user is found.
     *
     * @param login the user login.
     */
    Player getByLogin(String login);

    // List<user> getByActive(Boolean login);

    void save(Player player);

    void update(Player player);

    void delete(Player player);

    List<Player> findAll();
}
