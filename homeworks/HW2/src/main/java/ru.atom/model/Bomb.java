package ru.atom.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Bomb extends Field implements Positionable, Tickable {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private final int id;
    private Point point;
    private long time;

    public Bomb(int x, int y, long time) {
        super(x, y);
        this.id = getId();
        this.point = getPosition();
        log.info("Bombid = " + id + "; " + "Bomb place = (" + point.getX() + "," +
                point.getY() + ")" + "; " + "Bomb timer = " + time);
    }

    @Override
    public void tick(long elapsed) {
        if (time < elapsed) {
            time = 0;
        } else {
            time -= elapsed;
        }
    }
}
