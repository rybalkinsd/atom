package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import static model.GameConstants.DEFAULT_PLAYER_POSITION_X;
import static model.GameConstants.DEFAULT_PLAYER_POSITION_Y;
import static model.GameConstants.DEFAULT_RADIUS;

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
    private Double radius;
    private Coordinate coordinate;
    private Color color;

  /**
   * Create new Player
   *
   * @param name          visible name
   * @param x             x-coordinate
   * @param y             y-coordinate
   * @param radius        radius
   * @param color         color
   */
  public Player(@NotNull String name, Double x, Double y, Double radius, Color color) {
      this.name = name;
      this.radius = radius;
      coordinate = new Coordinate(x,y);
      this.color = color;
    if (log.isInfoEnabled()) {
      log.info(toString() + " created");
    }
  }
  /**
   * Create new Player
   *
   * @param name        visible name
   */
  public Player(@NotNull String name) {
    this.name = name;
    this.radius = DEFAULT_RADIUS;
    coordinate = new Coordinate(DEFAULT_PLAYER_POSITION_X,DEFAULT_PLAYER_POSITION_Y);
    this.color = Color.BLUE;
    if (log.isInfoEnabled()) {
      log.info(toString() + " created");
    }
  }

  @Override
  public String toString() {
    return "Player{" +
        "name = " + name + "; Color = " + color.name() + "; Coordinates = " + coordinate.toString() + "; Radius = " + this.radius.toString() +
        '}';
  }
}
