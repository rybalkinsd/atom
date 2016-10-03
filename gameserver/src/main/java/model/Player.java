package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Server player avatar
 * <a href="https://atom.mail.ru/blog/topic/update/39/">HOMEWORK 1</a> example game instance
 *
 * @author Alpi
 */
public class Player {
  @NotNull
  private final Logger log = LogManager.getLogger(Player.class);
  @NotNull
  private String name;

  private int mass;
  private int x;
  private int y;
  private double direction;

  /**
   * Create new Player
   *
   * @param name        visible name
   * @param initialMass starting player area
   */
  public Player(@NotNull String name, int initialMass) {
    this.name = name;
    this.mass = initialMass;
    if (log.isInfoEnabled()) {
      log.info("Player " + toString() + " created");
    }
  }

  public int getMass() {
    return mass;
  }

  public void setMass(int mass) {
    if (log.isInfoEnabled()) {
      log.info("Player [" + name + "] mass is now ");
    }
    this.mass = mass;
  }

  /**
   * @return orientation angle in radians
   */
  public double getDirection() {
    return direction;
  }

  public void setDirection(double direction) {
    this.direction = direction;
  }

  /**
   * @return x player position
   */
  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  /**
   * @return y player position
   */
  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  @NotNull
  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' +
        ", mass=" + mass +
        ", x=" + x +
        ", y=" + y +
        ", direction=" + direction +
        '}';
  }
}
