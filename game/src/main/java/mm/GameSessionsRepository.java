package mm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Repository
public class GameSessionsRepository {
    private BlockingQueue<GameSession> gameSessionsList = new LinkedBlockingQueue<>();

    @Bean
    public static GameSessionsRepository createGameSessionsRepository() {
        return new GameSessionsRepository();
    }

    public void put(GameSession gameSession) {
        gameSessionsList.offer(gameSession);
    }

    public void remove(GameSession gameSession) {
        gameSessionsList.remove(gameSession);
    }

    public GameSession get(long id) throws NoSuchFieldException {
        for (GameSession game: gameSessionsList)
            if (game.getId() == id)
                return game;
        throw new NoSuchFieldException("Game session ID: " + id + " not found");
    }

    public GameSession get(long minRating, long maxRating) {
        for (GameSession game: gameSessionsList) {
            if ((minRating < game.getAverageRating()) && (game.getAverageRating() < maxRating))
                return game;
        }
        return null; //no such game
    }

    public ArrayList <GameSession> returnIdleSessions() {
        ArrayList<GameSession> idleGames = new ArrayList<>();
        Date now = new Date();
        for (GameSession game: gameSessionsList) {
            if ((now.getTime() - game.getTimeOfLastAction().getTime()) > 30000) {
                idleGames.add(game);
            }
        }
        return idleGames;
    }
    public String toString() {
        String result = "";
        for (GameSession g:gameSessionsList) {
            result = result.concat(g.toString() + '\n');
        }
        return result;
    }
}
