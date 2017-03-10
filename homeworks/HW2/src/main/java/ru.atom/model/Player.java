package ru.atom.model;
import ru.atom.geometry.Point;

public class Girl implements Movable {

    private Point position;
    private long lifetime;
    private int velocity = 1;
    private final int id;

    public Girl(int x, int y) {
        this.position = new Point(x, y);
        this.lifetime = 0;
        this.id = GameSession.setObjectId();
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
    @Override
    public void tick(long elapsed) {
        lifetime += elapsed;
    }

    @Override
    public Point getPosition() {
        return position;
    }
    @Override
    public int setObject() {
        return id;
    }
 }