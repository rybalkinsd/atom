package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Wall extends Field implements Positionable {
    private static final Logger log = LogManager.getLogger(Wall.class);
    private final int id;
    private Point point;
    private final boolean breakable;

    public Wall(int x, int y, boolean breakable) {
        super(x, y);
        this.id = getId();
        this.point = getPosition();
        this.breakable = breakable;
        log.info("Wallid = " + id + "; " + "Wall place = (" + point.getX() + "," +
                point.getY() + ")" + "; " + "Breakable? = " + breakable);
    }
}
