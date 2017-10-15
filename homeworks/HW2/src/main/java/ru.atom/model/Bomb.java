package  ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bomb implements Positionable, Tickable {

    private static final Logger log = LogManager.getLogger(Bomb.class);
    private int id;
    private Point position;
    private int strenght;
    private long timer = 2000000;

    public Bomb(int id, Point position, int strenght) {
        this.id = id;
        this.position = position;
        this.strenght = strenght;
        log.info("Bomb id={} x={} y={} strength={} created",
                id, position.getX(), position.getY(), strenght);
    }

    public Bomb(int id, int x, int y, int strenght) {
        this.id = id;
        position = new Point(x,y);
        this.strenght = strenght;
        log.info("Bomb id={} x={} y={} strength={} created",
                id, position.getX(), position.getY(), strenght);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void tick(long elapsed) {
        if (timer < elapsed)
            timer = 0;
        else
            timer -= elapsed;
    }
}