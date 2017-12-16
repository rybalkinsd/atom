package matchmaker.dao;

import matchmaker.model.GameSession;
import org.springframework.data.repository.CrudRepository;

public interface GameSessionDao extends CrudRepository<GameSession, Integer> {
    GameSession getById(Long id);
}
