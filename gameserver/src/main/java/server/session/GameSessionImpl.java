package server.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import model.World;
import model.GameConstants;
import model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public class GameSessionImpl implements GameSession {

    @NotNull
    private static final Logger log = LogManager.getLogger(World.class);

    @NotNull
    private UUID sessionID;

    @NotNull
    private World world;

    public GameSessionImpl(@NotNull World world) {
        this.sessionID = UUID.randomUUID();
        this.world = world;
    }

    @NotNull
    public UUID getSessionID() {
         return sessionID;
    }

    public void setSessionID(@NotNull UUID sessionID) {
        this.sessionID = sessionID;
    }

    @NotNull
    @JsonIgnore //No serializer found for class model.Player
    public World getWorld() {
        return this.world;
    }

    public void setWorld(@NotNull World world) {
        this.world = world;
    }

    @Override
    public void join(@NotNull Player player) {
        if (world.getPlayers().size() < GameConstants.MAX_PLAYERS_IN_SESSION) {
            world.addPlayer(player);
            if (log.isInfoEnabled()) {
                log.info(player + "success join the game " + sessionID);
            }
        } else {
            if (log.isInfoEnabled()) {
                log.info(player + "NOT join the game " + sessionID);
            }
        }
    }
}
