package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Player implements Movable {

    private Point position;
    private final int playerId;
    private long playTime;
    private long towardsMoveTime;
    private Direction direction;
    private int velocity = 1;
    private static final Logger log = LogManager.getLogger(Player.class);

    public Player(Point position) {
        this.position = position;
        this.playerId = GameSession.getPlayerId();
        this.velocity = velocity;
        log.info("New player was created: id={}, position = ({},{})", playerId, position.getX(), position.getY());
    }

    
    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return playerId;
    }

    @Override
    public void tick(long elapsed) {
        playTime += elapsed;
    }

    @Override
    public Point move(Direction direction, long time) {
        switch (direction) {
            case UP:
                position = new Point(position.getX(), (int) (time * velocity + position.getY()));
                break;
            case DOWN:
                position = new Point(position.getX(), (int) (position.getY() - velocity * time));
                break;
            case RIGHT:
                position = new Point((int) (position.getX() + velocity * time), position.getY());
                break;
            case LEFT:
                position = new Point((int) (position.getX() - velocity * time), position.getY());
                break;
            case IDLE:
                position = new Point(position.getX(), position.getY());
                break;
            default:
                break;
        }
        return position;
    }
}
