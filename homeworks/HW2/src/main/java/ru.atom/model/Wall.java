package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

/**
 * Created by Fella on 14.03.2017.
 */
public class Wall implements Positionable {
    private static final Logger log = LogManager.getLogger(Wall.class);
    private final Point position;
    private final int id;

    public Wall(int x, int y) {
        this.position = new Point(x,y);
        this.id = GameSession.createId();
        log.info("New Wall. id=" + id + ", position=(" + x + ";" + y + ").");
    }


    @Override
    public int getId() {
        return 0;
    }


    @Override
    public Point getPosition() {
        return position;
    }

}
