package model;

import com.sun.istack.internal.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Server player avatar
 * <a href="https://atom.mail.ru/blog/topic/update/39/">HOMEWORK 1</a> example game instance
 *
 * @author Alpi
 */
public class Player extends GameObject{
  @NotNull
  private static final Logger log = LogManager.getLogger(Player.class);
  @NotNull
  private String name;
  @NotNull
  private double speed; //depends on the radius
  //

  /**
   * Create new Player
   *
   * @param name        visible name
   */
  public Player(@NotNull String name) {
    super(GameConstants.STARTING_RADIUS_OF_PLAYER);
    this.name = name;
    if (log.isInfoEnabled()) {
      log.info(toString() + " created");
    }
  }

  public void changeRadius(int amountOfFood) {
    this.radius += amountOfFood;
  }

  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' + " coordinates='" + x + " " + y + '\''
        + " radius='" + radius + '\'' + " color='" + color.toString() + '\'' + '}';
  }
}
