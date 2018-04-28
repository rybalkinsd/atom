package mm.Repo;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Repository
public class GameSessionsRepository {
    private BlockingQueue<GameSession> gameSessionsList = new LinkedBlockingQueue<>();

    public void put(GameSession gameSession) {
        gameSessionsList.offer(gameSession);
    }

    public void remove(GameSession gameSession) {
        gameSessionsList.remove(gameSession);
    }

    public boolean isEmpty() { return gameSessionsList.isEmpty(); }

    public GameSession get(long id) {
        for (GameSession game: gameSessionsList)
            if (game.getId() == id)
                return game;
        return null;
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
        String result = "Current game sessions queue:\n";
        for (GameSession g:gameSessionsList) {
            result = result.concat(g.toString() + '\n');
        }
        return result;
    }
}
