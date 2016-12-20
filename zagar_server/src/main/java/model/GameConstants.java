package model;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.time.Duration;

/**
 * @author apomosov
 */
public interface GameConstants {
    int TICKS_PER_SECOND = 20;
    int MAX_PLAYERS_IN_SESSION = 10;

    Dimension2D FIELD_SIZE = new Dimension(5_000, 5_000);

    double CELL_DENSITY = 0.005;
    int FOOD_MASS = 10;
    int DEFAULT_PLAYER_CELL_MASS = 40;
    int VIRUS_MASS = 100;

    double VIRUS_REMOVE_CHANCE = 0.1;
    int FOOD_PER_SECOND_GENERATION = 15;
    double FOOD_REMOVE_CHANCE = 0.1;

    int MASS_TO_EJECT = 3 * FOOD_MASS;
    //   int MASS_TO_SPLIT = 5 * DEFAULT_PLAYER_CELL_MASS;
    int MASS_TO_SPLIT = DEFAULT_PLAYER_CELL_MASS / 4;

    int MAX_FOOD_ON_FIELD = 100;
    int NUMBER_OF_VIRUSES = 3;

    Duration MOVEMENT_TIMEOUT = Duration.ofMinutes(2);
    double MAX_COORDINATE_DELTA_MODULE = 20;
    double INITIAL_SPEED = 500;
    double EJECTED_MASS_ACCELERATION = -50;

    Duration GENERATORS_PERIOD = Duration.ofSeconds(10);

}
