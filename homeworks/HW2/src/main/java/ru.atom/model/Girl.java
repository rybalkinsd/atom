package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by galina on 09.03.17.
 */
public class Girl implements Movable, Positionable {
    private int GirlId;
    private Point Position;
    private  long LifeTimeMs;

    public  Girl(Point pos) {
        this.Position = pos;
        this.LifeTimeMs = 0;
        GirlId = GameSession.getId();
    }
    @Override
    public int getId() {
        return GirlId;
    }

    @Override
    public void tick(long elapsed) {
        LifeTimeMs = LifeTimeMs + elapsed;
    }

    @Override
    public Point getPosition() {
        return Position;
    }

    @Override
    public Point move(Direction direction) {
        if (direction == Direction.DOWN)
            Position = new Point(Position.getX(), Position.getY()-1);
        if (direction == Direction.UP)
            Position = new Point(Position.getX(), Position.getY()+1);
        if (direction == Direction.LEFT)
            Position = new Point(Position.getX()-1, Position.getY());
        if (direction == Direction.RIGHT)
            Position = new Point(Position.getX()+1, Position.getY());
        return Position;
    }

    public long getLifeTimeMs() {
        return LifeTimeMs;
    }

    public void setLifeTimeMs(long lifeTimeMs) {
        LifeTimeMs = lifeTimeMs;
    }
}
