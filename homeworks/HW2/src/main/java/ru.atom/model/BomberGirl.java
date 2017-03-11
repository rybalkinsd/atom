package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Created by alex on 10.03.17.
 */
public class BomberGirl implements Movable {

    private static final Logger logger = LogManager.getLogger(BomberGirl.class);
    private final int id;
    private Point position;
    private int speed;
    private long passedTime;

    public BomberGirl(Point position, int speed) {
        if (speed <= 0) {
            logger.error("Girls speed must be > 0!");
            throw new IllegalArgumentException();
        }
        this.id = GameSession.setGameObjectId();
        this.position = position;
        this.speed = speed;
        logger.info("BomberGirl is created: id = {} x = {} y = {} speed = {}",
                getId(), position.getX(), position.getY(), speed);
    }

    public int getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public void tick(long elapsed) {
        passedTime += elapsed;

    }

    public int getSpeed() {
        return speed;
    }


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
