package bomber.matchmaker.dao;


import bomber.matchmaker.model.GameSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GameSessionDao extends CrudRepository<GameSession, Integer> {
    GameSession getByGameId(Integer gameId);
}
