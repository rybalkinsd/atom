package ru.atom.websocket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Bar;
import ru.atom.geometry.Point;

import static ru.atom.WorkWithProperties.getProperties;

/**
 * Created by BBPax on 06.03.17.
 */
public class Player extends AbstractGameObject implements Movable {
    private static final Logger log = LogManager.getLogger(Player.class);
    public static final int BAG_SIZE = Integer.valueOf(getProperties().getProperty("BAG_SIZE"));
    public static final int POWER_BOMB = Integer.valueOf(getProperties().getProperty("POWER_BOMB"));
    public static final int VELOCITY = Integer.valueOf(getProperties().getProperty("VELOCITY"));
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

    public int getVelocity() {
        return velocity;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public Player(int id, Point position) {
        super(id, position.getX(),position.getY());
        type = "Pawn";
        bagSize = BAG_SIZE;
        powerBomb = POWER_BOMB;
        velocity = VELOCITY;
        elapsedTime = 0L;
        direction = Direction.IDLE;
        plantBomb = false;
        bar = new Bar(
                new Point(position.getX() + CENTERED_BAR_SHIFT,
                        position.getY() + CENTERED_BAR_SHIFT), CENTERED_BAR_SIZE);
        log.info("Player(id = {}) is created in ( {} ; {} ) and bar {}",
                id, position.getX(), position.getY(), bar.toString());
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void tick(long elapsed) {
        if (elapsedTime <= 0L) {
            elapsedTime = 0L;
            if (bagSize == 0) {
                bagSize++;
            }
        } else {
            elapsedTime -= elapsed;
        }
        //log.info("time before bomb will appeared in my bag is {}", elapsedTime);
        position = move(direction);
        bar.setBarPosition(new Point(position.getX() + CENTERED_BAR_SHIFT, position.getY() + CENTERED_BAR_SHIFT));
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
        log.info("I, {}, will move in direction {} from ({};{})", getId(), direction, position.getX(), position.getY());
        return direction.move(position, velocity);
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
                Point centralPosition = new Point((position.getX() % BAR_SIZE < BAR_SIZE / 2)
                        ? position.getX() - position.getX() % BAR_SIZE : position.getX()
                        + BAR_SIZE - position.getX() % BAR_SIZE,
                        (position.getY() % BAR_SIZE < BAR_SIZE / 2) ? position.getY() - position.getY() % BAR_SIZE
                                : position.getY() + BAR_SIZE - position.getY() % BAR_SIZE);
                bagSize--;
                elapsedTime = 2000L;
                return new Bomb(0, centralPosition, powerBomb, this.getId());
            }
        } else {
            return null;
        }
    }

    public void getBonus(Bonus bonus) {
        if (bonus.getBonusType() == Bonus.Type.SPEED) {
            this.setVelocity(++velocity);
        } else if (bonus.getBonusType() == Bonus.Type.BOMB) {
            this.setBagSize(++bagSize);
        } else if (bonus.getBonusType() == Bonus.Type.RANGE) {
            this.setPowerBomb(++powerBomb);
        }
    }

    public void returnBomb() {
        setBagSize(++bagSize);
    }
}
