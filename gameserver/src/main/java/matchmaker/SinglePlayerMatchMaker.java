package matchmaker;

import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

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
        GameSession newGameSession =  new GameSession();
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
     * startNewGame function was deleted, because it calls only for GameSession constructor and was redundant.
     */
}
