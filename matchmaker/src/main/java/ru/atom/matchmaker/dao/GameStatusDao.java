package ru.atom.matchmaker.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.atom.matchmaker.model.GameStatus;

@Repository
public interface GameStatusDao extends CrudRepository<GameStatus, Integer> {
}
