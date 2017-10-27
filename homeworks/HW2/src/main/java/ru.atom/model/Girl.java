package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Girl implements GameObject, Tickable, Positionable, Movable {
    private static final Logger log = LogManager.getLogger(Girl.class);
    private Point position;
    private int id;
    private int speed;

    public Girl(Point position, int id) {
        this.position = position;
        this.id = id;
        this.speed = 1;
        log.info("Girl with id={} on position({},{})", id, position.getX(), position.getY());
    }

    public Point getPosition() {
        return position;
    }

    public Point move(Direction direction, long time) {
        int distance = (int) (speed * time);
        if (direction == direction.UP) position = new Point(position.getX(), position.getY() + distance);
        if (direction == direction.DOWN) position = new Point(position.getX(), position.getY() - distance);
        if (direction == direction.RIGHT) position = new Point(position.getX() + distance, position.getY());
        if (direction == direction.LEFT) position = new Point(position.getX() - distance, position.getY());

        log.info("Girl with id={} moved to position ({},{})", id, position.getX(), position.getY());
        return position;
    }

    public void tick(long elapsed) {
        log.info("tick = ", elapsed);
    }

    public int getId() {
        return id;
    }
}
