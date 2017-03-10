package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Created by Auerbah on 10.03.2017.
 */
public class Player extends PositionableGameObject implements Movable {

    public Player(Point position) {
        super(position);
    }

    public Player(int x, int y) {
        super(x, y);
    }

    @Override
    public void tick(long elapsed) {

    }

    @Override
    public Point move(Direction direction) {
        int x = position.getX();
        int y = position.getY();
        switch (direction) {
            case DOWN:
                y -= GameSession.DEFAULT_PLAYER_SPEED;
                break;
            case UP:
                y += GameSession.DEFAULT_PLAYER_SPEED;
                break;
            case RIGHT:
                x += GameSession.DEFAULT_PLAYER_SPEED;
                break;
            case LEFT:
                x -= GameSession.DEFAULT_PLAYER_SPEED;
                break;
            case IDLE:
                break;
        }
        position = new Point(x, y);
        return position;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + getId() +
                " position=(" + position.getX() + ";" + position.getY() +
                '}';
    }
}
