package matchmaker;

import model.World;
import model.Food;
import model.GameConstants;
import server.session.GameSession;
import model.Player;
import model.Position;
import model.Virus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import server.session.GameSessionImpl;

import java.util.*;

/**
 * Creates {@link GameSession} for single player
 *
 * @author Alpi
 */
public class SinglePlayerMatchMaker implements MatchMaker {
    @NotNull
    private final Logger log = LogManager.getLogger(SinglePlayerMatchMaker.class);
    @NotNull
    private final List<GameSession> activeGameSessions = new ArrayList<>();

    /**
     * Creates new GameSession for single player
     *
     * @param player single player
     */
    @Override
    public void joinGame(@NotNull Player player) {
        GameSession newGameSession = createNewGame();
        activeGameSessions.add(newGameSession);
        newGameSession.join(player);
    }

    @NotNull
    public List<GameSession> getActiveGameSessions() {
        return new ArrayList<>(activeGameSessions);
    }

    /**
     * TODO HOMEWORK 1. Implement new game creation. Instantiate GameSession state
     * Log every game instance creation
     *
     * @return new GameSession
     */
    @NotNull
    private GameSession createNewGame() {

        Random random = new Random();
        List<Food> foods = new ArrayList<>();
        for (int i = 0; i < GameConstants.INITIAL_FOODS_AMOUNT; i++) {
            foods.add(new Food(new Position(random.nextDouble() * GameConstants.MAX_BORDER_RIGHT,
                    random.nextDouble() * GameConstants.MAX_BORDER_TOP)));
        }

        List<Virus> viruses = new ArrayList<>();
        for (int i = 0; i < GameConstants.INITIAL_VIRUSES_AMOUNT; i++) {
            viruses.add(new Virus(
                    new Position(random.nextDouble() * GameConstants.MAX_BORDER_RIGHT,
                            random.nextDouble() * GameConstants.MAX_BORDER_TOP)));
        }

        World world = new World(foods, viruses);
        return new GameSessionImpl(world);

    }

}
