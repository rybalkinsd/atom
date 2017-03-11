package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Юля on 10.03.2017.
 */
public class Girl extends ObjectPosition implements Movable {
    private int velocity = 1;

    public Girl(int x, int y) {
        super();
        setPosition(new Point(x, y));
    }


    @Override
    public void tick(long elapsed) {
    }

    @Override
    public Point move(Direction direction) {
        switch (direction) {
            case DOWN:
                position.setY(position.getY() - velocity);
                break;
            case LEFT:
                position.setX(position.getX() - velocity);
                break;
            case RIGHT:
                position.setX(position.getX() + velocity);
                break;
            case UP:
                position.setY(position.getY() + velocity);
                break;
            default:
                break;
        }
        return new Point(position.getX(), position.getY());
    }
}
