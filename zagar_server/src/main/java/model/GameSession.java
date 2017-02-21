package model;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.List;
import java.util.Map;

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

    /**
     * Player leaves the GameSession
     *
     * @param player player to leave the game
     */
    void leave(@NotNull Player player);

    @NotNull
    Field getField();

    @NotNull
    List<Player> getPlayers();

    /**
     * Return top N users in session (by score, descending)
     *
     * @param n number of records to return
     * @return map of player`s scores (contains n top records)
     */
    @NotNull
    Map<Player, Integer> getTop(int n);

    void tickGenerators(@NotNull Duration elapsed);

    void tickRemoveAfk();
}
