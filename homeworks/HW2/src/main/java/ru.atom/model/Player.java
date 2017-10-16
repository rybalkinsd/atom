package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Player implements Movable {

    private int id;
    private Point position;
    private boolean isAlive;
    private int score;
    final Logger log = LogManager.getLogger(GameSession.class);

    public Player(int id, Point position) {
        this.id = id;
        this.position = position;
        log.info("Player with ID(" + id + ") was created on point("
                + position.getX() + "," + position.getY() + ")");
    }

    @Override
    public Point move(Direction direction, long time) {
        isAlive = true;
        int x = position.getX();
        int y = position.getY();
        switch (direction) {
            case UP:
                y += time;
                break;
            case DOWN:
                y -= time;
                break;
            case LEFT:
                x -= time;
                break;
            case RIGHT:
                x += time;
                break;
            case IDLE:
                break;
        }
        this.position = new Point(x, y);
        return position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        log.info("tick");
    }
}
