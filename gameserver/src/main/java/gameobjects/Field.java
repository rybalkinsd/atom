package gameobjects;

import geometry.Bar;
import geometry.Point;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Field implements Positionable {
    private Point position;
    private int x;
    private int y;
    private static AtomicInteger idGenerator = new AtomicInteger();

    private int id;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        this.position = new Point(x, y);
        this.id = idGenerator.getAndIncrement() + 1;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }
}
