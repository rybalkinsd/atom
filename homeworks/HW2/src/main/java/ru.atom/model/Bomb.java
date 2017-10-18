package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bomb implements Positionable, Tickable {

    private static final Logger logger = LogManager.getLogger(Bomb.class);
    private int id;
    private Point position;


    public Bomb(Point position, int id) {
        this.id = id;
        this.position = position;
        logger.info("Bomb is created: id = {} x = {} y = {}",
                getId(), position.getX(), position.getY());
    }

    public  int getId() {

        return id;
    }

    public void tick(long elapsed) {

        logger.info("tick {}",elapsed);
    }


    public Point getPosition() {

        return position;
    }


}
