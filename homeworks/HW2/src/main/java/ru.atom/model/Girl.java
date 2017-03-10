package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Юля on 10.03.2017.
 */
public class Girl extends ObjectPosition implements Movable {
    @Override
    public void tick(long elapsed) {
    }

    @Override
    public Point move(Direction direction) {
        switch (direction) {
            case DOWN:
                position.setY(position.getY() - 1);
                break;
            case LEFT:
                position.setX(position.getX() - 1);
                break;
            case RIGHT:
                position.setX(position.getX() + 1);
                break;
            case UP:
                position.setY(position.getY() + 1);
                break;
            default:
                break;
        }
        return null;
    }
}
