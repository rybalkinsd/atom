package ru.atom.model;


import ru.atom.geometry.Point;

public class Bomber implements Movable {
    private final int id;
    private Point position;
    private int velocity;
    private long time = 0L;

    public Bomber(int id, Point position, int velocity) {
        this.id = id;
        this.position = position;
        this.velocity = velocity;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        time += elapsed;
    }


    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public Point move(Direction direction) {
        switch (direction) {
            case UP:
                position = new Point(position.getX(), position.getY() + velocity);
                return position;
            case DOWN:
                position = new Point(position.getX(), position.getY() - velocity);
                return position;
            case LEFT:
                position = new Point(position.getX() - velocity, position.getY());
                return position;
            case RIGHT:
                position = new Point(position.getX() + velocity, position.getY());
                return position;
            case IDLE:
                return position;
            default:
                return position;
        }

    }

}
