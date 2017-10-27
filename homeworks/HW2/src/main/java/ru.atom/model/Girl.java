package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;


public class Girl implements Movable {
    private static final Logger log = LogManager.getLogger(Girl.class);
    private final int id;
    private Point point;
    private int speed;

    public Girl(int id, Point point, int speed) {
        this.id = id;
        this.point = point;
        this.speed = speed;
        log.info("Girl with id = " + id + " coordinates : " + point + " speed = " + speed);
    }

    @Override
    public Point getPosition() {
        return point;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point move(Direction direction, long time) {
        switch (direction) {
            case UP:
                point = new Point(point.getX(), point.getY() + (int) (time * speed));
                return point;
            case DOWN:
                point = new Point(point.getX(), point.getY() - (int) (time * speed));
                return point;
            case RIGHT:
                point = new Point(point.getX() + (int) (time * speed), point.getY());
                return point;
            case LEFT:
                point = new Point(point.getX() - (int) (time * speed), point.getY());
                return point;
            case IDLE:
                return point;
            default:
                return point;
        }
    }

    public void tick(long elapsed) {

    }
}