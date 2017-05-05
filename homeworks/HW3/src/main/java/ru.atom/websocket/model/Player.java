package ru.atom.websocket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

/**
 * Created by BBPax on 06.03.17.
 */
public class Player extends AbstractGameObject implements Movable {
    private static final Logger log = LogManager.getLogger(Player.class);
    @JsonIgnore
    private int bagSize;
    @JsonIgnore
    private int powerBomb;
    @JsonIgnore
    private int velocity;
    @JsonIgnore
    private long elapsedTime;
    @JsonIgnore
    private boolean plantBomb;
    @JsonIgnore
    private Direction direction;

    public void setBagSize(int bagSize) {
        this.bagSize = bagSize;
    }

    public void setPowerBomb(int powerBomb) {
        this.powerBomb = powerBomb;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public Player(int id, Point position) {
        super(id, position.getX(),position.getY());
        type = "Player";
        bagSize = 1;
        powerBomb = 1;
        velocity = 1;
        elapsedTime = 0L;
        direction = Direction.IDLE;
        plantBomb = false;
        log.info("Player(id = {}) is created in ( {} ; {} )", id, position.getX(), position.getY());
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void tick(long elapsed) {
        if (elapsedTime == 0L) {
            if(bagSize == 0) {
                bagSize++;
            }
        } else {
            elapsedTime -= elapsed;
        }
        position = move(direction);
        direction = Direction.IDLE;
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
        log.info("I'll move in direction {}", direction);
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

    public void plant() {
        plantBomb = true;
    }

    public Bomb plantBomb() {
        if (plantBomb) {
            plantBomb = false;
            if (bagSize == 0) {
                log.info(" I don't have bombs in bag");
                return null;
            } else {
                bagSize--;
                elapsedTime = 2L;
                return new Bomb(0, position, powerBomb);
            }
        } else {
            return null;
        }
    }
}
