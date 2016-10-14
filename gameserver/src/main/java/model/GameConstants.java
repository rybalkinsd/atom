package model;

import java.awt.*;
import java.util.Random;

/**
 * Global static constants
 *
 * @author apomosov
 */
public interface GameConstants {
    int INITIAL_CELL_MASS = 10;
    int INITIAL_VIRUS_MASS = 100;
    int FOOD_MASS = 2;
    int BLOB_MASS = 10;

    int INITIAL_FOODS_AMOUNT = 100;
    int INITIAL_VIRUSES_AMOUNT = 5;

    int MAX_PLAYERS_IN_SESSION = 10;
    int MAX_CELLS_AMOUNT = 16;

    int MAX_VIRUS_MASS = 400;
    int MAX_CELL_MASS = 22500;
}
