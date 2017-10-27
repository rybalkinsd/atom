package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Fire implements Positionable {

    private Point pointOfFire;
    private int id;

    private static final Logger log = LogManager.getLogger(Fire.class);

    public Fire() {
        pointOfFire = new Point(0,0);
        id = 0;
        log.info("fire object created");
    }

    @Override
    public Point getPosition() {
        return pointOfFire;
    }

    @Override
    public int getId() {
        return id;
    }
}
