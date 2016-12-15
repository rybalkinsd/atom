package matchmaker;

import gamemodel.Player;
import org.jetbrains.annotations.NotNull;
import server.model.user.User;
import session.GameSession;

import java.util.List;

/**
 * Provides (searches or creates) {@link GameSession} for {@link Player}
 *
 * @author Alpi
 */
public interface MatchMaker {
    /**
     * Searches available game session or creates new one
     *
     * @param player player to join the game session
     */
    void joinGame(@NotNull User user);

    void leaveGame(@NotNull User user);

    /**
     * @return Currently open game sessions
     */
    @NotNull
    List<GameSession> getActiveGameSessions();
}
