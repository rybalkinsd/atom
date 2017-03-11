package ru.atom.model;

import ru.atom.geometry.Point;

public class Girl extends PositionableObject implements Movable {

    Direction direction;

    public Girl() {
        this.setPosition(new Point(5, 5));
    }

    public Girl(Point pos) {
        this.setPosition(pos);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void tick(long elapsed) {
        move(direction);
    }

    @Override
    public Point move(Direction direction) {

        if (direction == Direction.UP) {
            this.setPosition(new Point(this.getPosition().getX(), this.getPosition().getY() + 1));
        } else if (direction == Direction.DOWN) {
            this.setPosition(new Point(this.getPosition().getX(), this.getPosition().getY() - 1));
        } else if (direction == Direction.RIGHT) {
            this.setPosition(new Point(this.getPosition().getX() + 1, this.getPosition().getY()));
        } else if (direction == Direction.LEFT) {
            this.setPosition(new Point(this.getPosition().getX() - 1, this.getPosition().getY()));
        }

        return this.getPosition();
    }

}
