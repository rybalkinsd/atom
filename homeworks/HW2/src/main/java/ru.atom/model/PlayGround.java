package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;
import ru.atom.geometry.Rectangle;

public class PlayGround implements GameObject {


    protected int id;
    protected Point position;
    protected Rectangle space;


    private static final Logger logger = LogManager.getLogger(Wall.class);


    public PlayGround(Point position, Rectangle space,int id) {
        this.id = id;
        this.position = position;
        this.space = space;
        logger.info("Yay! Now we have place to play № id={}, With edges={}", id, space);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public Rectangle getSpace() {
        return space;
    }
}