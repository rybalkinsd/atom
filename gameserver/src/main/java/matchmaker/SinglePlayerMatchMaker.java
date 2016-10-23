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
public class SinglePlayerMatchMaker implements IMatchMaker {
    @NotNull
    private final Logger log = LogManager.getLogger(SinglePlayerMatchMaker.class);
    @NotNull
    private final List<GameSession> activeGameSessions = new ArrayList<>();

    /**
     * Creates new GameSession for single player
     *
     * @param user single player
     */
    @Override
    public GameSession joinGame(@NotNull User user) {
        Player player = new Player(user);
        GameSession newGameSession =  new GameSession();
        activeGameSessions.add(newGameSession);
        newGameSession.join(player);
        if (log.isInfoEnabled()) {
            log.info(player + " joined " + newGameSession);
        }
        user.setSession(newGameSession);
        user.setPlayer(player);
        return newGameSession;
    }

    @Override
    public boolean leaveGame(@NotNull User user) {
        Player player = user.getPlayer();
        if (player != null) {
            for (GameSession session : activeGameSessions) {
                if (session.containsPlayer(player)) {
                    session.leave(player.getId());
                    user.setSession(null);
                    user.setPlayer(null);
                    return true;
                }
            }
        }
        return false;
    }

    @NotNull
    public List<GameSession> getActiveGameSessions() {
        return new ArrayList<>(activeGameSessions);
    }

    /**
     * startNewGame function was deleted, because it calls only for GameSession constructor and was redundant.
     */
}
