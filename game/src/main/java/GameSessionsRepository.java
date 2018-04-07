import org.springframework.stereotype.Repository;

import javax.validation.constraints.Null;
import java.util.ArrayList;

@Repository
public class GameSessionsRepository {
    private ArrayList<GameSession> gameSessionsList = new ArrayList<>();

    public void put(GameSession gameSession) {
        gameSessionsList.add(gameSession);
    }

    public void remove(GameSession gameSession) {
        gameSessionsList.remove(gameSession);
    }

    public GameSession get(long ID) throws NullPointerException {
        for (GameSession game: gameSessionsList)
            if(game.getID() == ID)
                return game;
        throw new NullPointerException("Game session ID: " + ID + " not found");
    }

    public GameSession get(long minRating, long maxRating) {
        for (GameSession game: gameSessionsList) {
            if ((minRating < game.getAverageRating()) && (game.getAverageRating() < maxRating))
                return game;
        }
        return null; //no such game
    }
}
