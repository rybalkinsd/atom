package model;

import com.sun.istack.internal.NotNull;

/**
 * Single agar.io game session
 * <p>Single game session take place in square map, where players battle for food
 * <p>Max {@link GameConstants#MAX_PLAYERS_IN_SESSION} players can play within single game session
 *
 * @author Alpi
 */
public interface GameSession {
  /**
   * Player can join session whenever there are less then {@link GameConstants#MAX_PLAYERS_IN_SESSION} players within game session
   *
   * @param player player to join the game
   */
  void join(@NotNull Player player);
  void addFood(@NotNull Food food);
  void addObstacle(@NotNull Obstacle obstacle);
}
