package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by pavel on 06.03.17.
 */
public class Girl extends AbstractGameObject implements Movable, Dieable {

    private static final Logger log = LogManager.getLogger(Girl.class);
    private Point position;
    private int id;
    private int speed;
    private Direction direction;
    private boolean isDead;

    public Girl(Point position) {
        this.position = position;
        this.id = AbstractGameObject.id++;
        this.speed = 1;
        this.direction = Direction.IDLE;
        this.isDead = false;
        log.info(this
                + " was created");
    }

    @Override
    public int getId() {
        return id;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void tick(long elapsed) {
        long countOfSteps = elapsed / (speed * 1000);
        for (long i = 0; i < countOfSteps; i++) {
            this.move(direction);
        }
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public Point move(Direction direction) {
        if (direction == Direction.DOWN) {
            position = new Point(position.getX(), position.getY() - speed);
            return position;
        } else if (direction == Direction.UP) {
            position = new Point(position.getX(), position.getY() + speed);
            return position;
        } else if (direction == Direction.LEFT) {
            position = new Point(position.getX() - speed, position.getY());
            return position;
        } else if (direction == Direction.RIGHT) {
            position = new Point(position.getX() + speed, position.getY());
            return position;
        } else if (direction == Direction.IDLE) {
            return position;
        } else {
            throw new NotImplementedException();
        }
    }

    @Override
    public String toString() {
        return "Girl{"
                + "id="
                + id
                + '}';
    }

    @Override
    public boolean isDead() {
        if (isDead) {
            log.info(this
                    + " is dead");
            return isDead;
        } else {
            return false;
        }
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
