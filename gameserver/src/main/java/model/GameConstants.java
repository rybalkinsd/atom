package model;

/**
 * Global static constants
 *
 * @author apomosov
 */
public interface GameConstants {
  int MAX_PLAYERS_IN_SESSION = 10;

  //later start coordinates of players will be defined in code (free space in game field)
  int DEFAULT_START_PLAYER_X_COORD = 0;
  int DEFAULT_START_PLAYER_Y_COORD = 0;

  int START_PLAYER_RADIUS = 100;
  int DEFAULT_PLAYER_COLOR = 0xFF0000;

  //characteristics of game field in pixels
  int WIDTH_OF_GAME_FIELD = 5000;
  int HEIGHT_OF_GAME_FIELD = 5000;
}
