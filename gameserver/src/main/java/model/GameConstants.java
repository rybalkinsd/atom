package model;

/**
 * Global static constants
 *
 * @author apomosov
 */
public interface GameConstants {
  int MAX_PLAYERS_IN_SESSION = 10; //not actual for single player game

  double GRID_SIZE = 1000; //not sure
  int PELLETS_NUM = 20; //I don't know how many pellets we need
  int VIRUSES_NUM = 5;

  //default values of mass
  int CELL_START_MASS=3;
  int PELLET_MASS=1;
  int VIRUS_MAX_MASS=50;
  int VIRUS_MIN_MASS=20;
}