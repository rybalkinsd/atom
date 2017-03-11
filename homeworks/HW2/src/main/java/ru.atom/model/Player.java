package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Vlad on 11.03.2017.
 */
public class Player extends AbstractGameObject implements Movable {
    private final int velocity = 10;
    private Direction direction = Direction.IDLE;

    public Player(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick(long elapsed) {
        move(direction);
    }

    @Override
    public Point move(Direction direction) {
        switch (direction) {
            case UP:
                position = new Point(position.getX(), position.getY() + velocity);
                break;
            case DOWN:
                position = new Point(position.getX(), position.getY() - velocity);
                break;
            case LEFT:
                position = new Point(position.getX() - velocity, position.getY());
                break;
            case RIGHT:
                position = new Point(position.getX() + velocity, position.getY());
                break;
            default:
                break;
        }
        return position;
    }
}
