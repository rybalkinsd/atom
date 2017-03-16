package ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Girl implements Movable {

    private static final Logger log = LogManager.getLogger(Girl.class);
    private Point position;
    private long lifetime;
    private int velocity;
    private int rangeExplosion;
    private int countBomb;
    private final int id;

    public Girl(int x, int y) {
        position = new Point(x, y);
        lifetime = 0L;
        velocity = 1;
        rangeExplosion = 1;
        countBomb = 1;
        id = GameSession.nextId();
        log.info("New Girl id=" + id + ", position=(" + x + "," + y + ")\n");
        log.info("velocity = " + velocity + "\n");
        log.info("rangeExplosion = " + rangeExplosion + "\n");
        log.info("countBomb = " + countBomb + "\n");
    }

    @Override
    public Point move(Direction direction) {
        switch (direction) {
            case UP:
                position = new Point(position.getX(), position.getY() + velocity);
                tick(1L);
                return position;
            case DOWN:
                position = new Point(position.getX(), position.getY() - velocity);
                tick(1L);
                return position;
            case RIGHT:
                position = new Point(position.getX() + velocity, position.getY());
                tick(1L);
                return position;
            case LEFT:
                position = new Point(position.getX() - velocity, position.getY());
                tick(1L);
                return position;
            case IDLE:
                tick(1L);
                return position;
            default:
                return position;
        }
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void tick(long elapsed) {
        lifetime += elapsed;
    }

    @Override
    public int getId() {
        return id;
    }

}
