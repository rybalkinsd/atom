package objects;

import dto.PawnDto;
import geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;

public class Player implements GameObject, Positionable, Movable, Tickable {
    private int id;
    private static AtomicInteger intId = new AtomicInteger();
    private String name;
    private double speed;
    private String direction;
    private Point position;
    private final Logger log = LogManager.getLogger(Player.class);
    private static ArrayList<Point> points = new ArrayList<Point>();
    private Point point1 = new Point(32,352);
    private Point point2 = new Point(470,352);
    private Point point3 = new Point(470,32);
    private Point point4 = new Point(32,32);


    public Player(String playerName) {
        name = playerName;
        speed = 0.4;
        id = intId.incrementAndGet();
        points.add(point1);
        points.add(point2);
        points.add(point3);
        points.add(point4);
        this.position = new Point(points.get(id).getX(),points.get(id).getY());
        log.info("new Player has been created");
    }


    public Player(String playerName, Point p) {
        name = playerName;
        id = intId.incrementAndGet();
        position = new Point(p.getX(), p.getY());
        log.info("new Player has been created");
    }


    public void setName(String playerName) {
        name = playerName;
    }

    public void setPosition(Point point) {
        position = point;
    }

    public void setSpeed(Double spd) {
        speed = spd;
    }

    public void setDirection(String direct) {
        direction = direct;
    }

    public double getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public void tick(long elapsed) {
        move(direction, elapsed);
    }

    @Override
    public Point move(String direction, long time) {
        if (Objects.equals(direction, "UP")) {
            position.setY(getPosition().getY() + speed * 10);
        }
        if (Objects.equals(direction, "DOWN")) {
            position.setY(getPosition().getY() - speed * 10);
        }
        if (Objects.equals(direction, "LEFT")) {
            position.setX(getPosition().getX() - speed * 10);
        }
        if (Objects.equals(direction, "RIGHT")) {
            position.setX(getPosition().getX() + speed * 10);
        }
        return getPosition();
    }

    public Point moveBack() {
        if (Objects.equals(direction, "UP")) {
            position.setY(getPosition().getY() - speed * 10);
        }
        if (Objects.equals(direction, "DOWN")) {
            position.setY(getPosition().getY() + speed * 10);
        }
        if (Objects.equals(direction, "LEFT")) {
            position.setX(getPosition().getX() + speed * 10);
        }
        if (Objects.equals(direction, "RIGHT")) {
            position.setX(getPosition().getX() - speed * 10);
        }
        return getPosition();
    }
}

