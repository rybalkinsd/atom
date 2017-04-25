package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by kinetik on 07.03.17.
 */
public class PlayerObject implements Movable, Positionable, Tickable, GameObject {

    private final int playerId;
    private int velocity;
    private Point position;
    private long tickValue;

    public PlayerObject(int playerId, int velocity, Point position, long tickValue) {
        this.playerId = playerId;
        if (velocity <= 0) {
            throw new IllegalArgumentException();
        }
        this.velocity = velocity;
        this.position = position;
        this.tickValue = tickValue;
    }

    @Override
    public int getId() {
        return this.playerId;
    }

    @Override
    public void tick(long elapsed) {
        this.tickValue = this.tickValue + elapsed;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public Point move(Direction direction) {
        switch (direction) {
            case UP:
                this.position = new Point(this.position.getX(), this.position.getY() + this.velocity);
                break;
            case DOWN:
                this.position = new Point(this.position.getX(), this.position.getY() - this.velocity);
                break;
            case LEFT:
                this.position = new Point(this.position.getX() - this.velocity, this.position.getY());
                break;
            case RIGHT:
                this.position = new Point(this.position.getX() + this.velocity, this.position.getY());
                break;
            default:
                break;
        }
        return this.position;
    }
}
