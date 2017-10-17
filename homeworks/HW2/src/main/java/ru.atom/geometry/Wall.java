package ru.atom.geometry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.GameObject;
import ru.atom.model.GameSession;

public class Wall implements GameObject {

    protected GameSession session;
    protected int id;
    protected Point position;
    protected Rectangle space;


    private static final Logger logger = LogManager.getLogger(Wall.class);


    public Wall(GameSession session,Point position, Rectangle space,int id) {
        this.id = id;
        this.position = position;
        this.space = space;
        logger.info("New gamezone id={}, With edges={}", id, space);
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