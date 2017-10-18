package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Girl implements Tickable, Positionable, GameObject, Movable {
    private static final Logger log = LogManager.getLogger(Girl.class);

    private Point position;
    private int id;
    private int speed;
    private int bombPower;
    private int bombNumber;

    public Girl(int id, Point pos, int initSpeed, int initPower, int initAmount) {
        this.id = id;
        this.position = pos;
        this.speed = initSpeed;
        this.bombNumber = initAmount;
        this.bombPower = initPower;
        log.info("Created new Girl: id = {} x = {} y = {} speed = {} power = {} amount = {}",
                id, pos.getX(), pos.getY(), speed, bombPower, bombNumber);
    }

    public void increaseSpeed() {
        this.speed++;
    }

    public void increasePower() {
        this.bombPower++;
    }

    public void increaseAmount() {
        this.bombNumber++;
    }

    public int getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public void tick(long elapsed) {
        log.info("tick {}", elapsed);
    }

    public Point move(Direction direction, long time) {
        int dist = (int) (speed * time);

        switch (direction) {
            case UP:
                position = new Point(position.getX(), position.getY() + dist);
                break;
            case DOWN:
                position = new Point(position.getX(), position.getY() - dist);
                break;
            case RIGHT:
                position = new Point(position.getX() + dist, position.getY());
                break;
            case LEFT:
                position = new Point(position.getX() - dist, position.getY());
                break;
            case IDLE:
                position = new Point(position.getX(), position.getY());
                break;
            default:
                return position;
        }

        return position;
    }

    public void dropBomb() {
        int someId = 0;
        new Bomb(someId, this.position, this.bombPower);
        this.bombNumber--;
    }
}