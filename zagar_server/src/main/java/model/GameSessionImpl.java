package model;

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
        player.setField(field);
        players.add(player);
        playerPlacer.place(player);
    }

    @Override
    public void leave(@NotNull Player player) {
        players.remove(player);
    }

    @Override
    @NotNull
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    @Override
    public void tickRemoveAfk() {
        players.removeIf(p -> System.currentTimeMillis() - p.lastMovementTime() > GameConstants.MOVEMENT_TIMEOUT.toMillis());
    }

    @Override
    public void tickGenerators(@NotNull Duration elapsed) {
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
