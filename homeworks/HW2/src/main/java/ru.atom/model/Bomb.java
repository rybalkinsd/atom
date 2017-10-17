package ru.atom.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Bomb implements Positionable, Tickable {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private final int ID;
    private Point point;
    private long time;

    public Bomb(int ID, Point point, long time) {
        this.ID = ID;
        this.point = point;
        this.time = time;
        log.info("BombID = " + ID + "; " + "Bomb place = (" + point.getX() + "," + point.getY() + ")" + "; " + "Bomb timer = " + time);
    }

    @Override
    public void tick(long elapsed) {
        if (time < elapsed) {
            time = 0;
        } else {
            time -= elapsed;
        }
    }

    @Override
    public Point getPosition() {
        return point;
    }

    @Override
    public int getId() {
        return ID;
    }
}
