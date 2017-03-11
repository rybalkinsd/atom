package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by ikozin on 10.03.17.
 */
public class Player implements Movable {

    private final int id;
    private Point position;
    private int velocity;
    private long timeStep;

    public Player(Point position, int velocity) {
        this.velocity = velocity;
        id = GameSession.getGameObjectId();
        this.position = position;
        timeStep = 0;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public Point move(Direction direction) {
        switch (direction) {
            case UP:
                position = new Point(position.getX(), position.getY() + velocity);
                break;
            case DOWN:
                position =  new Point(position.getX(), position.getY() - velocity);
                break;
            case LEFT:
                position = new Point(position.getX() - velocity, position.getY());
                break;
            case RIGHT:
                position = new Point(position.getX() + velocity, position.getY());
                break;
            case IDLE:
                break;
            default:
                throw new EnumConstantNotPresentException(Direction.class, "Unknown");
        }
        return position;
    }

    @Override
    public void tick(long elapsed) {
        timeStep = elapsed;
    }
}
