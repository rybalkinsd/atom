package model;

import main.ApplicationContext;
import network.ClientConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import utils.entityGeneration.FoodGenerator;
import utils.entityGeneration.VirusGenerator;
import utils.playerPlacing.PlayerPlacer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author apomosov
 */
public class GameSessionImpl implements GameSession {
    @NotNull
    private static final Logger log = LogManager.getLogger(GameSessionImpl.class);
    @NotNull
    private final Field field;
    @NotNull
    private final List<Player> players = new ArrayList<>();
    @NotNull
    private final FoodGenerator foodGenerator;
    @NotNull
    private final PlayerPlacer playerPlacer;
    @NotNull
    private final VirusGenerator virusGenerator;

    public GameSessionImpl(
            @NotNull Field field,
            @NotNull FoodGenerator foodGenerator,
            @NotNull PlayerPlacer playerPlacer,
            @NotNull VirusGenerator virusGenerator) {
        this.field = field;
        this.foodGenerator = foodGenerator;
        this.playerPlacer = playerPlacer;
        this.virusGenerator = virusGenerator;
    }

    @Override
    public void join(@NotNull Player player) {
        log.info("Player '{}' joined to session '{}'", player.getUser().getName(), this);
        player.setField(field);
        players.add(player);
        playerPlacer.place(player);
    }

    @Override
    public void leave(@NotNull Player player) {
        log.info("Player '{}' left from session '{}', closing connection", player.getUser().getName(), this);
        players.remove(player);
        //close connection
        ClientConnections cc = ApplicationContext.instance().get(ClientConnections.class);
        Session session = cc.getSessionByPlayer(player);
        if (session == null) {
            log.warn("Trying to close non-present session (player '{}')", player.getUser().getName());
        } else if (!session.isOpen()) {
            log.warn("Trying to close closed session (player '{}')", player.getUser().getName());
        } else {
            session.close();
        }
        cc.removeConnection(player);
    }

    @Override
    @NotNull
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    @Override
    public void tickRemoveAfk() {
        log.trace("Removing AFK players");
        long currentTime = System.currentTimeMillis();
        List<Player> afkPlayers = players.stream()
                .filter(player -> currentTime - player.lastMovementTime() > GameConstants.MOVEMENT_TIMEOUT.toMillis())
                .filter(player -> player.getCells().size() > 0) //remove only players who has one or more cell
                .collect(Collectors.toList());
        afkPlayers.forEach(this::leave);
    }

    @Override
    public void tickGenerators(@NotNull Duration elapsed) {
        log.trace("Tick generators");
        foodGenerator.tick(elapsed);
        virusGenerator.tick(elapsed);
    }

    @NotNull
    @Override
    public Map<Player, Integer> getTop(int n) {
        return players.stream()
                .sorted((player1, player2) -> player2.getTotalScore() - player1.getTotalScore())
                .limit(n)
                .collect(Collectors.toMap(player -> player, Player::getTotalScore));
    }

    @Override
    public @NotNull Field getField() {
        return field;
    }
}
