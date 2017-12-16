package ru.atom.matchmaker.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.atom.matchmaker.model.Game;

/**
 * Created by Alexandr on 25.11.2017.
 */
@Repository
public interface GameDao extends CrudRepository<Game, Long> {

}
