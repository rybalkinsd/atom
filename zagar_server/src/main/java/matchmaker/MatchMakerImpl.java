package matchmaker;

import accountserver.database.leaderboard.LeaderboardDao;
import main.ApplicationContext;
import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import utils.entityGeneration.FoodGenerator;
import utils.entityGeneration.RandomVirusGenerator;
import utils.entityGeneration.UniformFoodGenerator;
import utils.entityGeneration.VirusGenerator;
import utils.playerPlacing.PlayerPlacer;
import utils.playerPlacing.RandomPlayerPlacer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Creates {@link GameSession} for single player
 *
 * @author Alpi
 */
public class MatchMakerImpl implements MatchMaker {
    @NotNull
    private final Logger log = LogManager.getLogger(MatchMakerImpl.class);
    @NotNull
    private final List<GameSession> activeGameSessions = new ArrayList<>();

    /**
     * Creates new GameSession
     *
     * @param player single player
     */
    @Override
    public void joinGame(@NotNull Player player) {
        //try to find session with free slots
        List<GameSession> withFreeSlots = activeGameSessions.stream()
                .filter(s -> s.getPlayers().size() < GameConstants.MAX_PLAYERS_IN_SESSION)
                .collect(Collectors.toList());
        if (withFreeSlots.size() != 0) {
            withFreeSlots.get(0).join(player);
            player.setField(withFreeSlots.get(0).getField());
            log.info("{} joined to session with free slots {}", player, withFreeSlots.get(0));
            return;
        }
        GameSession newGameSession = createNewGame();
        activeGameSessions.add(newGameSession);
        newGameSession.join(player);
        player.setField(newGameSession.getField());
        log.info("{} joined {}", player, newGameSession);
    }

    @NotNull
    public List<GameSession> getActiveGameSessions() {
        return new ArrayList<>(activeGameSessions);
    }

    /**
     * @return new GameSession
     */
    private
    @NotNull
    GameSession createNewGame() {
        Field field = new Field();
        FoodGenerator foodGenerator = new UniformFoodGenerator(field,
                GameConstants.FOOD_PER_SECOND_GENERATION,
                GameConstants.MAX_FOOD_ON_FIELD,
                GameConstants.FOOD_REMOVE_CHANCE);
        PlayerPlacer playerPlacer = new RandomPlayerPlacer(field);
        VirusGenerator virusGenerator = new RandomVirusGenerator(field,
                GameConstants.NUMBER_OF_VIRUSES,
                GameConstants.VIRUS_REMOVE_CHANCE);
        return new GameSessionImpl(field, foodGenerator, playerPlacer, virusGenerator);
    }

    @Override
    public void leaveGame(@NotNull Player player) {
        //update score
        LeaderboardDao lb = ApplicationContext.instance().get(LeaderboardDao.class);
        lb.updateScore(player.getUser(), player.getTotalScore());
        activeGameSessions.forEach(session -> session.leave(player));
        //search and remove empty sessions
        activeGameSessions.removeIf(session -> session.getPlayers().isEmpty());
    }
}
