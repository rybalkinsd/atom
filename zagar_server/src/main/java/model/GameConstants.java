package model;

/**
 * @author apomosov
 */
public interface GameConstants {
  int MAX_PLAYERS_IN_SESSION = 10;
  int FIELD_WIDTH = 10000;
  int FIELD_HEIGHT = 10000;
  int FOOD_MASS = 10;
  int DEFAULT_PLAYER_CELL_MASS = 40;
  int VIRUS_MASS = 270;
  int FOOD_PER_SECOND_GENERATION = 1;
  int MAX_FOOD_ON_FIELD = 100;
  int NUMBER_OF_VIRUSES = 3;
  int MIN_MASS_FOR_EJECT = 80;
  int UNGOVERNABLE_TIME = 500;
  int MAX_DISCONNECTING_TIME = 6_000;
  int JOINING_TIME = 3_000;
  int MIN_MASS_TO_SPLIT = 100;
  double SPLIT_VELOCITY = 1;
  double SPLIT_FOOD_LIFETIME = 300;
  double RELOAD_EJECT_TIME = 500;

}
