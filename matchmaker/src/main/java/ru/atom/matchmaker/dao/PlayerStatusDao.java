package ru.atom.matchmaker.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.atom.matchmaker.model.PlayerStatus;

@Repository
public interface PlayerStatusDao extends CrudRepository<PlayerStatus, Integer> {
}
