package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Girl implements Movable {
    private static final Logger log = LogManager.getLogger(Girl.class);
    private Point position;
    private int speed = 1;
    private int id;

    public Girl(Point position,int id) {
        this.position = position;
        this.id = id;
        log.info("Girl with id={} on position({},{})",id,position.getX(),position.getY());
    }

    public Point move(Direction direction,long time) {
        int distance = (int) (speed * time);
        switch (direction) {
            case UP:
                position = new Point(position.getX(),position.getY() + distance);
                break;
            case DOWN:
                position = new Point(position.getX(),position.getY() - distance);
                break;
            case RIGHT:
                position = new Point(position.getX() + distance,position.getY());
                break;
            case LEFT:
                position = new Point(position.getX() - distance,position.getY());
                break;
            default:
                break;
        }
        log.info("id={} moved to({},{})",id,position.getX(),position.getY());
        return position;
    }

    public void tick(long elapsed) {
        try {
            Thread.sleep(elapsed);
            log.info("tick {}",elapsed);
        } catch (InterruptedException e) {
            throw new UnsupportedOperationException();
        }
    }

    public Point getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }
}
