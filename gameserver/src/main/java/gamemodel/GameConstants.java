package gamemodel;

/**
 * Global static constants
 */
public interface GameConstants {
    //For single player game value equals 1
    int MAX_PLAYERS_IN_SESSION = 1;
    int MAX_CELLS = 16;
    int FOOD_MASS_VALUE = 1;
    int BLOB_MASS_VALUE = 20;
    double BLOB_RADIUS = 0.3D * BLOB_MASS_VALUE;
    int STARTING_CELL_MASS_VALUE = 16;
    int STARTING_VIRUS_MASS_VALUE = 100;
    int MAX_VIRUS_MASS_VALUE = 400;
    int MAX_CELL_MASS_VALUE = 22500;
    int STARTING_FOODS_AMOUNT = 10;
    int STARTING_VIRUSES_AMOUNT = 5;
}
