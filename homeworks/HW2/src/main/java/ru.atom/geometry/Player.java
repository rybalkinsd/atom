package ru.atom.geometry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.GameObject;
import ru.atom.model.Movable;
import ru.atom.model.GameSession;


public class Player implements GameObject,Movable {

    private int speed = 1;
    private int bombCount = 1;
    private int attackRange = 1;
    private int id;
    protected Point position;
    protected Rectangle space;

    private static final Logger logger = LogManager.getLogger(Player.class);

    public Player(GameSession session, Point position,Rectangle space, int id) {
        this.id = id;
        this.position = position;
        this.space = space;
        logger.info("New BomberGirl id={}, starts at={}", id, position);

    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    @Override
    public Rectangle getSpace() {
        return space;
    }

    public int getId() {
        return id;
    }



    public void moveLog(String direction, int oldX, int oldY, int x, int y) {
        logger.info("BomberGirl {} moved {}! ({}, {}) to ({}, {})",
                id, direction, oldX, oldY, x, y);
    }


    public Point move(Movable.Direction direction, long time) {
        int localTime = Math.toIntExact(time);
        switch (direction) {
            case UP:
                moveLog(direction.toString(), position.getX(), position.getY(),
                        position.getX(), position.getY() + speed * (localTime));
                setPosition(new Point(position.getX(), position.getY() + speed * localTime));
                break;
            case DOWN:
                moveLog(direction.toString(), position.getX(), position.getY(),
                        position.getX(), position.getY() - speed * localTime);
                setPosition(new Point(position.getX(), position.getY() - speed * localTime));
                break;
            case RIGHT:
                moveLog(direction.toString(), position.getX(), position.getY(),
                        position.getX() + speed * localTime, position.getY());
                setPosition(new Point(position.getX() + speed * localTime, position.getY()));
                break;
            case LEFT:
                moveLog(direction.toString(), position.getX(), position.getY(),
                        position.getX() - speed * localTime, position.getY());
                setPosition(new Point(position.getX() - speed * localTime, position.getY()));
                break;
            default: return position;
        }
        return position;
    }

    public void tick(long elapsed) {
        throw new UnsupportedOperationException();
    }

}
