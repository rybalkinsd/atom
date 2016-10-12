package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.*;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Server player avatar
 * <a href="https://atom.mail.ru/blog/topic/update/39/">HOMEWORK 1</a> example game instance
 *
 * @author Alpi
 */
public class Player {

  @NotNull
  private static final Logger log = LogManager.getLogger(Player.class);

  @NotNull
  private String name;

  @NotNull
  private Set<Cell> cells = new HashSet<>(GameConstants.MAX_CELLS);


  /**
   * Create new Player
   *
   * @param name visible name
   */
  public Player(@NotNull String name) {
    this.name = name;
    Cell initialCell = new Cell(
            name,
            Color.getHSBColor(new Random().nextFloat(),
            new Random().nextFloat(), new Random().nextFloat()),
            new Position(new Random().nextDouble() * GameConstants.MAX_BORDER_RIGHT,
                    new Random().nextDouble() * GameConstants.MAX_BORDER_TOP));
    this.cells.add(initialCell);
    if (log.isInfoEnabled()) {
      log.info(toString() + " created");
    }
  }

  @Override
  public boolean equals(Object player) {
    if (this == player) return true;
    if (player == null || this.getClass() != player.getClass()) return false;

    Player currentPlayer = (Player) player;
    return this.name.equals(currentPlayer.name) && this.cells.equals(currentPlayer.cells);
  }

  @Override
  public int hashCode() {
    Random random = new Random();
    return random.hashCode();
  }

  @Override
  public String toString() {
    return "Player{" + "name='" + name +
            ", cells=" + cells +
            '}';
  }
}
