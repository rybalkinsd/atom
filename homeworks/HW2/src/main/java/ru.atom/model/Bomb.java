package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Bomb implements Tickable, Positionable, GameObject {
    private static final Logger log = LogManager.getLogger(Bomb.class);

    private Point position;
    private int id;
    private int power;

    public Bomb(int id, Point pos, int power) {
        this.position = pos;
        this.id = id;
        this.power = power;
        log.info("Created new Bomb: id = {} x = {} y = {} power = {}",
                id, pos.getX(), pos.getY(), power);
    }

    public int getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public void tick(long elapsed) {
        log.info("tick {}", elapsed);
    }
}