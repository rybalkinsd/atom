package ru.atom.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.atom.model.Game;

public interface GameDao extends CrudRepository<Game, Long> {
}