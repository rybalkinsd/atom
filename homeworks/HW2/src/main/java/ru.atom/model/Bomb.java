package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Bomb implements Tickable, Positionable {
    private static final Logger log = LogManager.getLogger(Bomb.class);

    private int x;
    private int y;
    private int id;
    private int durability;//vrema zhizni na zabugornom


    public Bomb(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.durability = 1000;
        log.info("new Bomb");
    }

    public int getId() {
        return id;
    }

    public Point getPosition() {
        Point point = new Point(x, y);
        return point;

    }

    public void tick(long elapsed) {
        log.info("tick");

    }
}
