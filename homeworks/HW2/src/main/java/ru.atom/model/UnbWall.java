package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;


public class UnbWall implements Positionable {
    private int x;
    private int y;
    private int id;
    private boolean breakable;

    public UnbWall(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.breakable = true;
        log.info("new unbreakable wall");
    }

    private final  Logger log = LogManager.getLogger(UnbWall.class);

    public int getId() {
        return id;
    }

    public boolean durability() {
        return breakable;
    }

    public Point getPosition() {
        Point point = new Point(x, y);
        return point;

    }


}
