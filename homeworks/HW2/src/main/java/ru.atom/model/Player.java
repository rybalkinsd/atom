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
        int positionX = position.getX();
        int positionY = position.getY();
        switch (direction) {
            case DOWN:
                positionY -= GameSession.DEFAULT_PLAYER_SPEED;
                break;
            case UP:
                positionY += GameSession.DEFAULT_PLAYER_SPEED;
                break;
            case RIGHT:
                positionX += GameSession.DEFAULT_PLAYER_SPEED;
                break;
            case LEFT:
                positionX -= GameSession.DEFAULT_PLAYER_SPEED;
                break;
            case IDLE:
                break;
            default:
                break;
        }
        position = new Point(positionX, positionY);
        return position;
    }

    @Override
    public String toString() {
        return "Player{"
                + "id=" + getId()
                + " position=(" + position.getX() + ";" + position.getY()
                + '}';
    }
}
