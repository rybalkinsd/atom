package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Wall implements Positionable {

    private static final Logger log = LogManager.getLogger(Wall.class);

    public static Long id = 10000000000L;
    private Point position;

    public Wall(Point position) {

        this.id += 1000000;
        this.position = position;
        log.info("New Wall id={}, position={}", id, position);
    }



    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public long getId() {
        return id;
    }
}
