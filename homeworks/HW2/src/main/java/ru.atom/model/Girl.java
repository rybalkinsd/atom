package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Girl implements Movable {
    private static final Logger log = LogManager.getLogger(Girl.class);
    private final int id;
    private Point position;
    private int speed;
    private int powerBomb;
    private long elapsedTime;

    public Girl(int x, int y, int id) {
        if (x < 0 || y < 0 || id < 0) {
            log.error("Invalid arguments");
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.position = new Point(x,y);
        this.powerBomb = 1;
        this.speed = 1;
        this.elapsedTime = 0L;
        log.info("Girl (id = {}) was created in ( {} ; {} )", id, position.getX(), position.getY());
    }

    @Override
    public void tick(long elapsed) {
        this.elapsedTime += elapsed;
    }

    @Override
    public Point move(Direction direction) {
        if (direction == null) {
            log.error("Direction is null");
            throw new NullPointerException();
        }
        switch (direction) {
            case UP:
                return new Point(this.getPosition().getX(), this.getPosition().getY() + speed);
            case DOWN:
                return new Point(this.getPosition().getX(), this.getPosition().getY() - speed);
            case LEFT:
                return new Point(this.getPosition().getX() - speed, this.getPosition().getY());
            case RIGHT:
                return new Point(this.getPosition().getX() + speed, this.getPosition().getY());
            case IDLE:
                return getPosition();
            default:
                return getPosition();
        }
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public int getPowerBomb() {
        return powerBomb;
    }

    public void increasePowerBomb(int powerBomb) {
        this.powerBomb += powerBomb;
    }

    public int getSpeed() {
        return speed;
    }

    public void increaseSpeed(int speed) {
        this.speed += speed;
    }
}