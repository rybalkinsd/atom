package ru.atom.model.players;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;
import ru.atom.model.GameSession;
import ru.atom.model.Movable;

/**
 * Created by IGIntellectual on 10.03.2017.
 */
public class Player implements Movable {
    private static final Logger LOG = LogManager.getLogger(Player.class);

    private Point position;
    private int lifeTime;
    private int power;
    private int velocity;
    private int id;

    public Player(int x, int y) {
        this.position = new Point(x, y);
        this.lifeTime = 0;
        power = 1;
        velocity = 1;
        this.id = GameSession.getId();
        LOG.info("Player new ( id = {} ) is created in ( {};{} ) ",
                this.id, getPosition().getX(), getPosition().getY());
    }

    public void setPowerBomb(int power) {
        this.power = power;
    }

    @Override
    public void tick(long elapsed) {
        lifeTime += elapsed;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point move(Direction direction) {
        if (direction == null) {
            LOG.error("Player: direction null");
            throw new NullPointerException("ой всё");
        }
        switch (direction) {
            case UP:
                return new Point(getPosition().getX(), getPosition().getY() + velocity);
            case DOWN:
                return new Point(getPosition().getX(), getPosition().getY() - velocity);
            case RIGHT:
                return new Point(getPosition().getX() + velocity, getPosition().getY());
            case LEFT:
                return new Point(getPosition().getX() - velocity, getPosition().getY());
            case IDLE:
                return getPosition();
            default:
                return getPosition();
        }
    }
}
