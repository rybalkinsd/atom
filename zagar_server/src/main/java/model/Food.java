package model;

import org.jetbrains.annotations.NotNull;

import java.awt.geom.Point2D;

/**
 * @author apomosov
 */
public class Food extends Cell {
    public Food(@NotNull Point2D coordinate) {
        super(coordinate, GameConstants.FOOD_MASS);
    }
}
