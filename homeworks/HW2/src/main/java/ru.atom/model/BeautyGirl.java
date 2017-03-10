package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by home on 10.03.2017.
 */
public class BeautyGirl extends AbstractGameObject implements Movable {
    private static final Logger log = LogManager.getLogger(BeautyGirl.class);

    private int velocity = 1;
    private long girlLifeTime;
    private int powerBomb;
    private Point newPosition;

    public BeautyGirl(Point position) {
        super(position.getX(), position.getY());
        this.velocity = 1;
        this.newPosition = new Point(position.getX(), position.getY());
        powerBomb = 3;
        girlLifeTime = 0L;
        log.info("New BeautyGirl was created id={}, x={}, y={}, StartPowerBomb={}, StartVelocity={}",
                getId(), position.getX(), position.getY(), powerBomb, velocity);
    }

    @Override
    public Point getPosition() {
        return newPosition;
    }

    @Override
    public void tick(long elapsed) {
        girlLifeTime += elapsed;
    }

    @Override
    public Point move(Direction direction) {
        if (direction == null) {
            log.error("direction have null pointer");
            throw new NullPointerException();
        }

        switch (direction) {
            case UP:
                return new Point(newPosition.getX(), newPosition.getY() + velocity);
            case DOWN:
                return new Point(newPosition.getX(), newPosition.getY() - velocity);
            case LEFT:
                return new Point(newPosition.getX() - velocity, newPosition.getY());
            case RIGHT:
                return new Point(newPosition.getX() + velocity, newPosition.getY());
            case IDLE:
                return newPosition;
            default:
                return newPosition;
        }

    }
}
