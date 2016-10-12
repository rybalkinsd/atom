package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;

public class Virus {
    @NotNull
    private static final Logger log = LogManager.getLogger(Virus.class);

    @NotNull
    private Position position;

    private int mass = GameConstants.VIRUS_MASS;

    @NotNull
    private Color color = Color.GREEN;

    public Virus(@NotNull Position position) {
        this.position = position;
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public boolean equals(Object viruses) {
        if (this == viruses) return true;
        if (viruses == null || this.getClass() != viruses.getClass()) return false;

        Virus currentViruses = (Virus) viruses;
        return position.equals(currentViruses.position);
    }

    @Override
    public int hashCode() {
        Random random = new Random();
        return random.hashCode();
    }

    @Override
    public String toString() {
        return "Virus(" +
                "position=" + position +
                ", mass=" + mass +
                ')';
    }
}
