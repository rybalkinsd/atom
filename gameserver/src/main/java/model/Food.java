package model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Created by Klissan on 09.10.2016.
 */
public class Food
        extends GameEntity
{
    @NotNull
    private final static Logger log = LogManager.getLogger(Food.class);

    Food(Point2D coordinates, Color color, int mass) {
        super(coordinates, color, mass);
    }
}
