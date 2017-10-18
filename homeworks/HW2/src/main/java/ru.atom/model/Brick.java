package ru.atom.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Brick implements Tickable, Positionable {

    private static final Logger logger = LogManager.getLogger(Brick.class);
    private int id;
    private Point position;

    public Brick(Point position, int id) {

        this.id = id;
        this.position = position;
        logger.info("Brick is created: id={}, position={}", id, position.getX(), position.getY());
    }

    @Override
    public Point getPosition() {

        return position;
    }

    @Override
    public int getId() {

        return id;
    }


    @Override
    public void tick(long elapsed) {

        logger.info("tick {}",elapsed);
    }
}