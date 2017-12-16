package gameservice.model;

import gameservice.geometry.Point;
import org.slf4j.LoggerFactory;

public class Pawn extends GameObject implements Movable, Tickable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Pawn.class);

    private static final int PAWN_WIDTH = 27;
    private static final int PAWN_HEIGHT = 27;
    private Direction direction = Direction.IDLE;
    private transient int bombCapacity = 1;

    public Pawn(GameSession session, Point position) {
        super(session, new Point(position.getX() * GameObject.getWidthBox(),
                        position.getY() * GameObject.getWidthBox()),
                "Pawn", PAWN_WIDTH, PAWN_HEIGHT);
        log.info("New Pawn id={}, position={}, session_ID = {}", id, position, session.getId());
    }

    @Override
    public Point move(int time) {
        int delta = time / 8;
        switch (direction) {
            case UP:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX(), position.getY() + delta);
                setPosition(new Point(position.getX(), position.getY() + delta));
                break;
            case DOWN:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX(), position.getY() - delta);
                setPosition(new Point(position.getX(), position.getY() - delta));
                break;
            case RIGHT:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX() + delta, position.getY());
                setPosition(new Point(position.getX() + delta, position.getY()));
                break;
            case LEFT:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX() - delta, position.getY());
                setPosition(new Point(position.getX() - delta, position.getY()));
                break;
            default:
                return position;
        }
        return position;
    }

    public Point moveBack(int time) {
        int delta = time / 8;
        switch (direction) {
            case DOWN:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX(), position.getY() + delta);
                setPosition(new Point(position.getX(), position.getY() + delta));
                setDirection(Direction.IDLE);
                break;
            case UP:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX(), position.getY() - delta);
                setPosition(new Point(position.getX(), position.getY() - delta));
                setDirection(Direction.IDLE);
                break;
            case LEFT:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX() + delta, position.getY());
                setPosition(new Point(position.getX() + delta, position.getY()));
                setDirection(Direction.IDLE);
                break;
            case RIGHT:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX() - delta, position.getY());
                setPosition(new Point(position.getX() - delta, position.getY()));
                setDirection(Direction.IDLE);
                break;
            case IDLE:
                return position;
            default:
                return position;
        }
        return position;
    }

    public void moveLog(Direction direction, int oldX, int oldY, int x, int y) {
        log.info("Pawn id = {} moved {} ({}, {}) to ({}, {})",
                getId(), direction.name(), oldX, oldY, x, y);
    }

    @Override
    public void tick(int elapsed) {
        move(elapsed);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getBombCapacity() {
        return bombCapacity;
    }
}
