package matchmaker;


import model.GameSession;
import model.Player;
import model.User;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MatchMaker implements IMatchMaker {

    private List<GameSession> sessions;

    public MatchMaker() {
        sessions = new ArrayList<>();
    }

    @NotNull
    public GameSession joinGame(@NotNull User user) {
        Player player = new Player(user);
        for(GameSession session: sessions) {
            if (!session.isFull()) {
                session.join(player);
                user.setPlayer(player);
                user.setSession(session);
                return session;
            }
        }
        GameSession session = new GameSession();
        session.join(player);
        sessions.add(session);
        user.setPlayer(player);
        user.setSession(session);
        return session;
    }

    public boolean leaveGame(@NotNull User user) {
        Player player = user.getPlayer();
        if (player != null) {
            for (GameSession session : sessions) {
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

    /**
     * @return Currently open game sessions
     */
    @NotNull
    public List<GameSession> getActiveGameSessions() {
        return this.sessions;
    }
}
