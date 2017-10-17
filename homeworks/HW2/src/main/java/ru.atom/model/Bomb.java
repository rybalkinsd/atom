package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Bomb implements Positionable {

    private Point pointOfBomb;
    private int id;

    private static final Logger log = LogManager.getLogger(Bomb.class);

    public Bomb() {
        pointOfBomb = new Point(0,0);
        id = 1;
        log.info("bomb object created");
    }

    @Override
    public Point getPosition() {
        return pointOfBomb;
    }

    @Override
    public int getId() {
        return id;
    }
}