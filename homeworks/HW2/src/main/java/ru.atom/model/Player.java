package ru.atom.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Player implements Movable {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private final int ID;
    private Point point;
    private long time;
    private int speed;

    public Player(int ID, Point point, int speed) {
        this.ID = ID;
        this.point = point;
        this.speed = speed;
        log.info("PlayerID = " + ID + "; " + "Player place = (" + point.getX() + "," + point.getY() + ")" + "; " + "Player speed = " + speed);
    }

    @Override
    public Point move(Direction direction, long time) {
        switch (direction) {
            case UP:
                point = new Point(point.getX(), point.getY() + (int) (speed * time));
                break;
            case DOWN:
                point = new Point(point.getX(), point.getY() - (int) (speed * time));
                break;
            case RIGHT:
                point = new Point(point.getX() + (int) (speed * time), point.getY());
                break;
            case LEFT:
                point = new Point(point.getX() - (int) (speed * time), point.getY());
                break;
            default:
                break;
        }
        log.info("id={} moved to({},{})", ID, point.getX(), point.getY());
        return point;
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
