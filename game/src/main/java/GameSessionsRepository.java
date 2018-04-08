import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Repository
public class GameSessionsRepository {
    private BlockingQueue<GameSession> gameSessionsList = new LinkedBlockingQueue<>();

    @Bean
    public GameSessionsRepository createGameSessionsRepository() {
        return new GameSessionsRepository();
    }

    public void put(GameSession gameSession) {
        gameSessionsList.offer(gameSession);
    }

    public void remove(GameSession gameSession) {
        gameSessionsList.remove(gameSession);
    }

    public GameSession get(long ID) throws NoSuchFieldException{
        for (GameSession game: gameSessionsList)
            if(game.getID() == ID)
                return game;
        throw new NoSuchFieldException("Game session ID: " + ID + " not found");
    }

    public GameSession get(long minRating, long maxRating) {
        for (GameSession game: gameSessionsList) {
            if ((minRating < game.getAverageRating()) && (game.getAverageRating() < maxRating))
                return game;
        }
        return null; //no such game
    }
}
