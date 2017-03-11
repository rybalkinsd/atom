package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by dmbragin on 3/11/17.
 */
public class Hero extends PositionalGameObject implements Movable {
    private static final int defaultVelocity = 1;
    private int velocity;

    public Hero(int x, int y) {
        super(x, y);
        velocity = defaultVelocity;
    }

    public Hero(Point position) {
        super(position);
        velocity = defaultVelocity;
    }

    @Override
    public void tick(long elapsed) {
        // At this moment I dont know what should be here
    }

    @Override
    public Point move(Direction direction) {
        switch (direction) {
            case UP:
                this.position = new Point(position.getX(), position.getY() + velocity);
                break;
            case DOWN:
                this.position = new Point(position.getX(), position.getY() - velocity);
                break;
            case LEFT:
                this.position = new Point(position.getX() - velocity, position.getY());
                break;
            case RIGHT:
                this.position = new Point(position.getX() + velocity, position.getY());
                break;
            default:
                break;
        }

        return position;
    }
}
