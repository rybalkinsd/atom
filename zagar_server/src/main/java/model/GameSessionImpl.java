package model;

import org.jetbrains.annotations.NotNull;
import utils.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author apomosov
 */
public class GameSessionImpl implements GameSession {
  private static final IDGenerator idGenerator = new SequentialIDGenerator();
  private final int id = idGenerator.next();
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

  public GameSessionImpl(@NotNull Field field,@NotNull FoodGenerator foodGenerator, @NotNull PlayerPlacer playerPlacer, @NotNull VirusGenerator virusGenerator) {
    this.foodGenerator = foodGenerator;
    this.playerPlacer = playerPlacer;
    this.virusGenerator = virusGenerator;
    this.field = field;
    virusGenerator.generate();
  }

  @Override
  public void join(@NotNull Player player) {
    players.add(player);
    this.playerPlacer.place(player);
  }

  @Override
  public void leave(@NotNull Player player) {
    players.remove(player);
  }

  @Override
  public List<Player> getPlayers() {
    return new ArrayList<>(players);
  }

  @Override
  public Field getField() {
    return field;
  }

  @Override
  public String[] getLeaders() {
      return players.stream()
              .sorted(Comparator.comparing(Player::getScore).reversed())
              .limit(10)
              .map(player -> player.getName()) //(Player::getName)
              .toArray(String[]::new);
  }

  public FoodGenerator getFoodGenerator() {
    return foodGenerator;
  }

  public VirusGenerator getVirusGenerator() {
    return virusGenerator;
  }

  @Override
  public String toString() {
    return "GameSessionImpl{" +
        "id=" + id +
        '}';
  }
}
