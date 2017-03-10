package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

/**
 * Created by BBPax on 06.03.17.
 */
public class Player extends AbstractGameObject implements Movable {
    private static final Logger log = LogManager.getLogger(Player.class);
    private int bagSize;
    private int powerBomb;
    private int velocity;
    private long elapsedTime;

    public void setBagSize(int bagSize) {
        this.bagSize = bagSize;
    }

    public void setPowerBomb(int powerBomb) {
        this.powerBomb = powerBomb;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public Player(int id, Point position) {
        super(id, position.getX(),position.getY());
        bagSize = 1;
        powerBomb = 1;
        velocity = 1;
        elapsedTime = 0L;
        log.info("Player(id = {}) is created in ( {} ; {} )", id, position.getX(), position.getY());
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void tick(long elapsed) {
        elapsedTime += elapsed;
    }

    @Override
    public Point getPosition() {
        return super.getPosition();
    }

    @Override
    public Point move(Direction direction) {
        if (direction == null) {
            log.error("direction shouldn't be null");
            throw new NullPointerException();
        }
        switch (direction) {
            case UP:
                return new Point(this.getPosition().getX(), this.getPosition().getY() + velocity);
            case DOWN:
                return new Point(this.getPosition().getX(), this.getPosition().getY() - velocity);
            case IDLE:
                return getPosition();
            case LEFT:
                return new Point(this.getPosition().getX() - velocity, this.getPosition().getY());
            case RIGHT:
                return new Point(this.getPosition().getX() + velocity, this.getPosition().getY());
            default:
                return getPosition();
        }
    }
}
