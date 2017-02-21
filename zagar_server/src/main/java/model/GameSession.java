package model;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Single agar.io game session
 * <p>Single game session take place in square map, where players battle for food
 * <p>Max {@link GameConstants#MAX_PLAYERS_IN_SESSION} players can play within single game session
 *
 * @author Alpi
 */
public interface GameSession {

  void join(@NotNull Player player);

  void leave(@NotNull Player player);

  List<Player> getPlayers();
  GameField getField();

  void offerNewLocation(Player player, double dx, double dy);

  void tick();
}
