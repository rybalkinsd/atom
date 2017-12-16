package objects;

import dto.ObjectDto;
import org.apache.logging.log4j.LogManager;
import geometry.Point;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Wood implements GameObject, Positionable {
    /*
    * just a wood. can be destroyed by bomb
    */
    private int id;
    private static AtomicInteger intId = new AtomicInteger();
    private Point position;
    private static final Logger log = LogManager.getLogger(Player.class);

    public Wood(Point p) {
        this.id = intId.incrementAndGet();
        this.position = new Point(p.getX(), p.getY());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point point) {
        position.setX(point.getX());
        position.setY(point.getY());
    }


}

