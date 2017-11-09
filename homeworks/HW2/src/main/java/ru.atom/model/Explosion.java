package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;
import ru.atom.geometry.Rectangle;


public class Explosion implements GameObject,Tickable {


    protected int id;
    protected Point position;
    protected Rectangle space;


    private static final Logger logger = LogManager.getLogger(Wall.class);


    public Explosion(Point position, Rectangle space,int id) {
        this.id = id;
        this.position = position;
        this.space = space;
        logger.info("BANG!!New explosion {}, covers {}", id, space);
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

    public void tick(long elapsed) {
        throw new UnsupportedOperationException();
    }
}

