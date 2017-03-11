package ru.atom.model;

import ru.atom.geometry.Point;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by ruslbizh on 11.03.2017.
 */
public class Girl implements Positionable, Movable {

    private static final Logger log = LogManager.getLogger(Girl.class);
    private int id;
    private Point position;
    private int speed;
    private int elapsedTime;

    public Girl(int x, int y, int speed) {
        if (speed <= 0) {
            log.error("Speed must be positive");
            throw new IllegalArgumentException();
        }
        this.position = new Point(x,y);
        this.id = GameSession.createObjecId();
        this.speed = speed;
        this.elapsedTime = 0;
        log.info("Girl was created with parameters: id = {}, position = ({}, {}), speed = {}",
                id, position.getX(), position.getY(), this.speed);
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
        elapsedTime += elapsed;
    }

    @Override
    public Point move(Direction direction) {
        switch (direction) {
            case UP:
                this.position = new Point(this.position.getX(), this.position.getY() + this.speed);
                break;
            case DOWN:
                this.position = new Point(this.position.getX(), this.position.getY() - this.speed);
                break;
            case RIGHT:
                this.position = new Point(this.position.getX() + this.speed, this.position.getY());
                break;
            case LEFT:
                this.position = new Point(this.position.getX() - this.speed, this.position.getY());
                break;
            case IDLE:
                this.position = new Point(this.position.getX(), this.position.getY());
                break;
            default:
                return  this.position;
        }
        return this.position;
    }
}
