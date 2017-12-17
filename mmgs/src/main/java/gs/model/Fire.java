package gs.model;

import gs.tick.Tickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import gs.geometry.Point;

public class Fire extends Field implements Positionable, Tickable {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private final int id;
    private Point point;
    private long currentTime = 0;
    private final long lifeTime = 600;
    private boolean doubleExplosion = false;

    public Fire(int x, int y) {
        super(x, y);
        this.id = getId();
        this.point = getPosition();
        log.info("Explosionid = " + id + "; " + "Fire place = (" + point.getX() + "," +
                point.getY() + ")" + "; " + "Fire timer = " + lifeTime);
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

    public void resurrect() {
        currentTime = 0;
    }

    public void setDoubleExplosion(boolean doubleExplosion) {
        this.doubleExplosion = doubleExplosion;
    }

    public boolean isDoubleExplosion() {
        return doubleExplosion;
    }

    public String toJson() {
        Point pos = getPosition();
        return "{\"type\":\"" + this.getClass().getSimpleName() + "\",\"id\":" +
                this.getId() + ",\"position\":{\"x\":" + pos.getX() + ",\"y\":" + pos.getY() + "}}";
    }
}
