package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import ru.atom.geometry.Point;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Bomb implements GameObject, Positionable, Tickable {
    private final int id;
    private Point position;
    private static final Logger log = LogManager.getLogger(Player.class);

    public Bomb(int id, Point p) {
        this.id = id;
        this.position = new Point(p.getX(), p.getY());
        log.info("new Bomb has been created");
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public void tick(long elapsed){
        //delete bomb and destroy boxes
    }
}
