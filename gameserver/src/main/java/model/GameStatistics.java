package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class GameStatistics {

    @NotNull
    private static final Logger log = LogManager.getLogger(GameStatistics.class);

    private long foodEaten = 0;
    private int highestMass = GameConstants.STARTING_CELL_MASS_VALUE;
    private long timeAlive = System.currentTimeMillis();
    private long cellsEaten = 0;
    private int topPosition = 0;

    public GameStatistics() {
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "GameStatistics{" +
                "foodEaten=" + foodEaten +
                ", highestMass=" + highestMass +
                ", timeAlive=" + timeAlive +
                ", cellsEaten=" + cellsEaten +
                ", topPosition=" + topPosition +
                '}';
    }
}
