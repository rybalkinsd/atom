package session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import model.Field;
import model.GameConstants;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSessionImpl implements GameSession {

    private static final Logger log = LogManager.getLogger(GameSessionImpl.class);

    @NotNull
    private UUID sessionID;

    //For single player
    @NotNull
    private List<Player> players = new ArrayList<>(GameConstants.MAX_PLAYERS_IN_SESSION);

    @NotNull
    private Field field;

    public GameSessionImpl(@NotNull Field field) {
        this.sessionID = UUID.randomUUID();
        this.field = field;
    }

    @NotNull
    public UUID getSessionID() {
        return sessionID;
    }

    public void setSessionID(@NotNull UUID sessionID) {
        this.sessionID = sessionID;
    }

    @NotNull
    @JsonIgnore
    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(@NotNull Player player) {
        players.add(player);
    }

    @NotNull
    @JsonIgnore
    public Field getField() {
        return field;
    }

    public void setField(@NotNull Field field) {
        this.field = field;
    }

    @Override
    public void join(@NotNull Player player) {
        if (players.size() < GameConstants.MAX_PLAYERS_IN_SESSION) {
            players.add(player);
            if (log.isInfoEnabled()) {
                log.info(player + " successfully joined the session.");
            }
        } else {
            if (log.isWarnEnabled()) {
                log.warn("Session " + this.sessionID + " is full.");
            }
        }
    }
}
