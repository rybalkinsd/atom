package ru.atom.model;

import ru.atom.geometry.Point;
import ru.atom.geometry.Rectangle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Brick implements GameObject {


    protected int id;
    protected Point position;
    protected Rectangle space;


    private static final Logger logger = LogManager.getLogger(Wall.class);


    public Brick(Point position, Rectangle space,int id) {
        this.id = id;
        this.position = position;
        this.space = space;
        logger.info("New brick id={}, With edges={}", id, space);
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
