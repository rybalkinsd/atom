package ru.atom.model;

import ru.atom.geometry.Point;
import ru.atom.model.GameObject;
import ru.atom.model.GameSession;
import ru.atom.model.Movable;
import ru.atom.model.Positionable;

/**
 * Created by dmitriy on 05.03.17.
 */
public class Girl implements GameObject, Positionable, Movable {

    private Point position;
    private int step = 1;
    private Direction direction = Direction.IDLE;
    private final int id;

    public Girl(int x, int y) {
        this.position = new Point(x, y);
        id = GameSession.nextValue();
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
            case DOWN:
                position = new Point(position.getX(), position.getY() - step);
                break;
            case UP:
                position = new Point(position.getX(), position.getY() + step);
                break;
            case LEFT:
                position = new Point(position.getX() - step, position.getY());
                break;
            case RIGHT:
                position = new Point(position.getX() + step, position.getY());
                break;
            default:
                break;
        }
        return position;
    }

    @Override
    public void tick(long elapsed) {
        move(direction);
    }
}
