package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by galina on 09.03.17.
 */
public class Girl implements Movable, Positionable {
    private int girlId;
    private Point position;
    private  long lifeTimeMs;

    public  Girl(Point pos) {
        this.position = pos;
        this.lifeTimeMs = 0;
        girlId = GameSession.getId();
    }

    @Override
    public int getId() {
        return girlId;
    }

    @Override
    public void tick(long elapsed) {
        lifeTimeMs = lifeTimeMs + elapsed;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public Point move(Direction direction) {
        if (direction == Direction.DOWN)
            position = new Point(position.getX(), position.getY() - 1);
        if (direction == Direction.UP)
            position = new Point(position.getX(), position.getY() + 1);
        if (direction == Direction.LEFT)
            position = new Point(position.getX() - 1, position.getY());
        if (direction == Direction.RIGHT)
            position = new Point(position.getX() + 1, position.getY());
        return position;
    }

    public long getLifeTimeMs() {
        return lifeTimeMs;
    }

    public void setLifeTimeMs(long lifeTimeMs) {
        this.lifeTimeMs = lifeTimeMs;
    }
}
