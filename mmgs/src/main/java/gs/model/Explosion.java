package gs.model;

import gs.tick.Tickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import gs.geometry.Point;

public class Explosion extends Field implements Positionable, Tickable {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private final int id;
    private Point point;
    private long time;

    public Explosion(int x, int y, long time) {
        super(x, y);
        this.id = getId();
        this.point = getPosition();
        log.info("Explosionid = " + id + "; " + "Explosion place = (" + point.getX() + "," +
                point.getY() + ")" + "; " + "Explosion timer = " + time);
    }

    @Override
    public void tick(long elapsed) {
        if (time < elapsed) {
            time = 0;
        } else {
            time -= elapsed;
        }
    }

    public String toJson() {
        Point pos = getPosition();
        String obj = "{\"type\":\"" + this.getClass().getSimpleName() + "\",\"id\":" +
                this.getId() + ",\"position\":{\"x\":" + pos.getX() + ",\"y\":" + pos.getY() + "}}";
        return obj;
    }
}
