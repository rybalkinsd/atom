package model;

import org.jetbrains.annotations.NotNull;

import java.awt.geom.Point2D;

/**
 * @author apomosov
 */
public class Virus extends Cell {
    public Virus(@NotNull Point2D coordinate) {
        super(coordinate, GameConstants.VIRUS_MASS);
    }
}
