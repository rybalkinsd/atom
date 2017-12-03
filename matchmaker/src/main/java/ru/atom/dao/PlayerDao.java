package ru.atom.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.atom.model.Player;

public interface PlayerDao extends CrudRepository<Player, Integer> {
    public Player getByName(String name);
}
