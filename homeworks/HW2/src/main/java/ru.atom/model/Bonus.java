package ru.atom.model;

import ru.atom.geometry.Point;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Bonus implements GameObject, Positionable, Tickable {
    private final int id;
    private final int type;
    private Point position;
    private static final Logger log = LogManager.getLogger(Player.class);


    public Bonus(int id, int type, Point p) {
        this.id = id;
        this.type = type;
        this.position = new Point(p.getX(), p.getY());
        log.info("new Bonus has been created");
    }

    @Override
    public int getId() {
        return this.id;
    }

    public int getType() {
        return this.type;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public void tick(long elapsed){
    }
}
