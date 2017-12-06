package gs.model;

import gs.geometry.Bar;
import gs.geometry.Point;

public abstract class GameObject {
    protected final GameSession session;
    protected final int id;
    protected Point position;
    private final int width;
    private final int height;

    public GameObject(GameSession session, Point position, int width, int height) {
        this.position = position;
        this.id = session.getNewId();
        this.session = session;
        this.width = width;
        this.height = height;
    }

    public Bar getBar() {
        return new Bar(position.getX(), position.getY(), position.getX() + width, position.getY() + height);
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }
}