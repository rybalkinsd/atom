package ru.atom.matchmaker.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.atom.matchmaker.model.Player;
import ru.atom.matchmaker.model.PlayerStatus;

import java.util.Set;

/**
 * Created by Alexandr on 25.11.2017.
 */
@Repository
public interface PlayerDao extends CrudRepository<Player, Integer> {

    public Player getByLogin(String login);

    public Player getByLoginAndPassword(String login, String password);

    public Set<Player> findByLoginIn(Iterable<String> logins);

    public Set<Player> findTop10ByOrderByWinsDesc();

    public Set<Player> findByStatusEquals(PlayerStatus playerStatus);

}