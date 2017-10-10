package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Girl extends GameObject implements Movable {
    private static final Logger logger = LogManager.getLogger(Girl.class);

    private int speed = 30;
    private int bombCapacity = 1;
    private int bombRange = 1;

    public Girl(GameSession session, Point position) {
        super(session, position);
        logger.info("New Girl id={}, position={}", getId(), position);
    }

    @Override
    public Point move(Direction direction, int time) {
        switch (direction) {
            case UP:
                moveLog(direction.toString(), position.getX(), position.getY(),
                        position.getX(), position.getY() + speed * time);
                setPosition(new Point(position.getX(), position.getY() + speed * time));
                break;
            case DOWN:
                moveLog(direction.toString(), position.getX(), position.getY(),
                        position.getX(), position.getY() - speed * time);
                setPosition(new Point(position.getX(), position.getY() - speed * time));
                break;
            case RIGHT:
                moveLog(direction.toString(), position.getX(), position.getY(),
                        position.getX() + speed * time, position.getY());
                setPosition(new Point(position.getX() + speed * time, position.getY()));
                break;
            case LEFT:
                moveLog(direction.toString(), position.getX(), position.getY(),
                        position.getX() - speed * time, position.getY());
                setPosition(new Point(position.getX() - speed * time, position.getY()));
                break;
        }
        return position;
    }

    @Override
    public void tick(int elapsed) {
        throw new UnsupportedOperationException();
    }

    public void moveLog(String direction, int oldX, int oldY, int x, int y) {
        logger.info("Girl id = {} moved {}! ({}, {}) to ({}, {})",
                getId(), direction, oldX, oldY, x, y);
    }
}
