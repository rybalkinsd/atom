package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Wall implements Positionable {
    private static final Logger log = LogManager.getLogger(Wall.class);
    private final int ID;
    private Point point;
    private final boolean breakable;

    public Wall(int ID, Point point, boolean breakable) {
        this.ID = ID;
        this.point = point;
        this.breakable = breakable;
        log.info("WallID = " + ID + "; " + "Wall place = (" + point.getX() + "," + point.getY() + ")" + "; " + "Breakable? = " + breakable);
    }

    @Override
    public Point getPosition() {
        return point;
    }

    @Override
    public int getId() {
        return ID;
    }
}
