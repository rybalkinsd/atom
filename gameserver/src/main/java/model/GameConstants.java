package model;

import java.time.Duration;

/**
 * Global static constants
 *
 * @author apomosov
 */
public interface GameConstants {
  int MAX_PLAYERS_IN_SESSION = 10;
  int PLAYERS_IN_SINGLEPLAYER = 1;

  int START_CELL_MASS = 10;
  int FOOD_MASS = 1;
  int START_BUSH_MASS = 100;
  int MASS_NEEDED_TO_SHARE = 36;
  int SPLITABLE_MASS = 20;
  int BOARD_OF_LOSE_MASS = 100;
  int BUSH_CRITICAL_MASS = 200;
  float PERCENT_OF_STABLE_MASS = 0.98f;

  double BASE_SPEED = 100.0d;
  Duration SPLICE_COOLDOWN = Duration.ofMinutes(1);
}
