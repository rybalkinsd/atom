package model;

import org.jetbrains.annotations.NotNull;
import utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author apomosov
 */
public class GameSessionImpl implements GameSession {
  private static final IDGenerator idGenerator = new SequentialIDGenerator();
  private final int id = idGenerator.next();
  @NotNull
  private final Field field;
  @NotNull
  private final List<Player> players = new CopyOnWriteArrayList<>();
  @NotNull
  private final FoodGenerator foodGenerator;
  @NotNull
  private final PlayerPlacer playerPlacer;

  public GameSessionImpl(@NotNull Field field,@NotNull FoodGenerator foodGenerator, @NotNull PlayerPlacer playerPlacer) {
    this.foodGenerator = foodGenerator;
    this.playerPlacer = playerPlacer;
    this.field = field;
    foodGenerator.run();
  }



  @Override
  public void join(@NotNull Player player) {
    players.add(player);
    this.playerPlacer.place(player);
  }
  @Override
  public void respawn(@NotNull Player player){
    player.startRespawn();
    playerPlacer.place(player);
  }

  @Override
  public void leave(@NotNull Player player) {
    players.remove(player);
  }

  @Override
  public List<Player> getPlayers() {
    return new ArrayList<>(players);
  }

  @NotNull
  @Override
  public Field getField() {
    return field;
  }

  @Override
  public String toString() {
    return "GameSessionImpl{" +
        "id=" + id +
        '}';
  }

}
