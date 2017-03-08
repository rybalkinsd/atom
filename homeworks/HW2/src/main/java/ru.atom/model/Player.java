package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Western-Co on 08.03.2017.
 */
public class Player implements Movable {
    private int id;
    private Point position;
    private int velocity;
    private int bombStrength;
    private static final int MAX_BOMB = 1;
    private long currentTime;

    public Player(Point position, int velocity) {
        if (velocity <= 0) {
            throw new IllegalArgumentException();
        }
        this.id = GameSession.setObjectId();
        this.position = position;
        this.velocity = velocity;
        this.currentTime = 0L;
        bombStrength = 1;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        this.currentTime += elapsed;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public Point move(Direction direction) {
        return direction.move(this.position, this.velocity);
    }
}
