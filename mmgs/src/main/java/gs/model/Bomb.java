package gs.model;


import gs.tick.Tickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import gs.geometry.Point;

import java.util.Comparator;

public class Bomb extends Field implements Positionable, Tickable {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private final int id;
    private Point point;
    private long currentTime = 0;
    private int power = 1;
    private long lifeTime;
    private boolean doubleFire = false;

    public Bomb(int x, int y, long time) {
        super(x, y);
        this.id = getId();
        this.point = getPosition();
        this.lifeTime = time;
        log.info("Bombid = " + id + "; " + "Bomb place = (" + point.getX() + "," +
                point.getY() + ")" + "; " + "Bomb timer = " + time);
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public void setDoubleFire(boolean doubleFire) {
        this.doubleFire = doubleFire;
    }

    public boolean isDoubleFire() {
        return doubleFire;
    }

    @Override
    public void tick(long elapsed) {
        currentTime += elapsed;
    }

    public long getLifetimeMillis() {
        return lifeTime;
    }

    public boolean isDead() {
        return currentTime >= lifeTime;
    }

    public String toJson() {
        Point pos = getPosition();
        return "{\"type\":\"" + this.getClass().getSimpleName() + "\",\"id\":" +
                this.getId() + ",\"position\":{\"x\":" + pos.getX() + ",\"y\":" + pos.getY() + "}}";
    }
}
