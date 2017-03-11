package ru.atom.model;
import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Player extends AbstractGameObject implements Movable {
    private static final Logger log = LogManager.getLogger(Player.class);
    private int velocity = 1;
    private long playerLifeTime;
    private int boomPower;
    private Point newPosition;

    public Player(Point position) {
        super(position.getX(), position.getY());
        this.velocity = 1;
        this.newPosition = new Point(position.getX(), position.getY());
        boomPower = 3;
        playerLifeTime = 0L;
        log.info("New player was created id={}, x={}, y={}, StartPowerBomb={}, StartVelocity={}",
                getId(), position.getX(), position.getY(), boomPower, velocity);
    }

    @Override
    public void tick(long elapsed) {
        playerLifeTime += elapsed;
    }

    @Override
    public Point getPosition() {
        return newPosition;
    }

    @Override
    public Point move(Direction direction) {
        if (direction == null) {
            log.error("direction has null pointer");
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