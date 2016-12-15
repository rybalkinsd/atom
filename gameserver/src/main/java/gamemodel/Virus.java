package gamemodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

public class Virus {

    @NotNull
    private static final Logger LOG = LogManager.getLogger(Virus.class);

    @NotNull
    private final Color color = Color.GREEN;

    @NotNull
    private Position position;

    private int mass = GameConstants.STARTING_VIRUS_MASS_VALUE;

    private double speed;

    public Virus(@NotNull Position position, double speed) {
        this.position = position;
        this.speed = speed;
        if (LOG.isInfoEnabled()) {
            LOG.info(toString() + " created");
        }
    }

    public static void x(){}

    @Override
    public String toString() {
        return "Virus{" +
                "color=" + color +
                ", position=" + position +
                ", mass=" + mass +
                ", speed=" + speed +
                '}';
    }
}
