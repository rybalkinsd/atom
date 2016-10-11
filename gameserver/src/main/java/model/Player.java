package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.rolling.action.Duration;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

import static model.GameConstants.START_CELL_MASS;

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
  private int foodEaten;
  private int highestMass;
  private LocalDateTime timeStart;
  private Duration leaderboardTime;
  private int cellsEaten;
  private int topLeaderboardPosition;



  //TODO maybe we need something else here?

  /**
   * Create new Player
   *
   * @param name        visible name
   */
  public Player(@NotNull String name) {
    this.name = name;
    this.foodEaten = 0;
    this.highestMass = START_CELL_MASS;
    this.timeStart = LocalDateTime.now();
    this.leaderboardTime = Duration.ZERO;
    this.cellsEaten = 0;
    this.topLeaderboardPosition = 0;//todo get lastPosition;
    if (log.isInfoEnabled()) {
      log.info(toString() + " created");
    }
  }

  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' +
        '}';
  }
}
