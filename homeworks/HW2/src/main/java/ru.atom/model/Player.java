package ru.atom.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Player extends Field implements Movable {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private final int id;
    private Point point;
    private long time;
    private int speed;

    public Player(int x, int y, int speed) {
        super(x, y);
        this.id = getId();
        this.speed = speed;
        this.point = getPosition();
        log.info("Playerid = " + id + "; " + "Player place = (" + point.getX() + "," +
                point.getY() + ")" + "; " + "Player speed = " + speed);
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
        log.info("id={} moved to({},{})", id, point.getX(), point.getY());
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
}
