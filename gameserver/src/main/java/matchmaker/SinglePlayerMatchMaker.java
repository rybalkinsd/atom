package matchmaker;

import gamemodel.Field;
import gamemodel.Food;
import gamemodel.GameConstants;
import gamemodel.Position;
import gamemodel.Virus;
import gamemodel.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import session.GameSession;
import session.GameSessionImpl;
import utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        GameSession newGameSession = initializeNewGame();
        activeGameSessions.add(newGameSession);
        newGameSession.join(player);
        if (log.isInfoEnabled()) {
            log.info(player + " joined " + newGameSession);
        }
    }

    @NotNull
    public List<GameSession> getActiveGameSessions() {
        return new ArrayList<>(activeGameSessions);
    }

    /**
     * Log every game instance creation
     *
     * @return new GameSession
     */
    @NotNull
    private GameSession initializeNewGame() {

        List<Food> foods = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < GameConstants.STARTING_FOODS_AMOUNT; i++) {
            foods.add(new Food(ColorUtils.generateRandomColor(),
                    new Position(random.nextInt(Field.BORDER_RIGHT), random.nextInt(Field.BORDER_TOP))));
        }

        List<Virus> viruses = new ArrayList<>();
        for (int i = 0; i < GameConstants.STARTING_VIRUSES_AMOUNT; i++) {
            viruses.add(new Virus(
                    new Position(random.nextInt(Field.BORDER_RIGHT), random.nextInt(Field.BORDER_TOP)), 0.0D));
        }

        Field field = new Field(foods, viruses);
        return new GameSessionImpl(field);

    }

}
