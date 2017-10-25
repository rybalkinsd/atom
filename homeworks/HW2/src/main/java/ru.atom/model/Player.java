package ru.atom.model;

import ru.atom.geometry.Point;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player implements GameObject, Positionable, Movable, Tickable {
    private final int id;
    private Point position;
    private static final Logger log = LogManager.getLogger(Player.class);

    public Player(int id) {
        this.id = id;
        this.position = new Point(0, 0);
        log.info("new Player has been created");
    }

    public Player(int id, Point p) {
        this.id = id;
        this.position = new Point(p.getX(), p.getY());
        log.info("new Player has been created");
    }

    private void setPosition(Point point) {
        this.position = point;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public void tick(long elapsed){}

    @Override
    public Point move(Direction direction, long time) {
        for (long tm = 0; tm < time; tm += 100) {
            if (direction == Direction.UP) {
                setPosition(new Point(getPosition().getX(), getPosition().getY() + 1));
            }
            if (direction == Direction.DOWN) {
                setPosition(new Point(getPosition().getX(), getPosition().getY() - 1));
            }
            if (direction == Direction.LEFT) {
                setPosition(new Point(getPosition().getX() - 1, getPosition().getY()));
            }
            if (direction == Direction.RIGHT) {
                setPosition(new Point(getPosition().getX() + 1, getPosition().getY()));
            }
        }
        return getPosition();
    }
}
