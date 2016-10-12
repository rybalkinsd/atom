package model;

/**
 * Global static constants
 *
 * @author apomosov
 */
public interface GameConstants {
    int MAX_PLAYERS_IN_SESSION = 10;
    int FOOD_MASS_UNIT = 1;
    int CELL_MASS = 5;
    int VIRUS_MASS = 10;
    int MAX_CELLS = 15;
    double MAX_BORDER_TOP = 1000.0D;
    double MAX_BORDER_RIGHT = 1000.0D;
    double MIN_BORDER_TOP = 0.0D;
    double MIN_BORDER_RIGHT = 0.0D;
    int INITIAL_FOODS_AMOUNT = 1000;
    int INITIAL_VIRUSES_AMOUNT = 100;
}
