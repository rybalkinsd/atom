package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

import java.util.ArrayList;
import java.util.List;

public class Girl implements Movable {

    private static final Logger logger = LogManager.getLogger(Girl.class);

    private final int id;
    private Point position;
    private final int velocity;
    private int maxBombs;
    private List<Bomb> bombs;

    public Girl(int id, Point position, int velocity, int maxBombs) {
        this.id = id;
        this.position = position;
        this.velocity = velocity;
        this.maxBombs = maxBombs;
        bombs = new ArrayList<>();

        logger.info(toString());
    }

    // TODO:
    //      public boolean canPutsBomb() - check return bombs.size() < maxBombs;
    //      public Bomb putBomb() - create new bomb and add it to bombs list
    //      fields bombPower, bombLifetime

    @Override
    public Point move(Direction direction, long time) {
        int dx = position.getX();
        int dy = position.getY();
        long dist = velocity * time;
        switch (direction) {

            case UP:
                dy += dist;
                break;
            case DOWN:
                dy -= dist;
                break;
            case RIGHT:
                dx += dist;
                break;
            case LEFT:
                dx -= dist;
                break;
            case IDLE:
                break;
            default:
                break;
        }
        position = new Point(dx, dy);
        // TODO: like this
        /*
            if (checkPosition(nextPosition)) {
                position = nextPosition;
            }
         */
        return position;
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
    public void tick(long elapsed) {
        bombs.removeIf(Bomb::isDead);
    }

    @Override
    public String toString() {
        return "[Girl: id=" + String.valueOf(id) + " pos=" + position.toString() + "]";
    }
}
