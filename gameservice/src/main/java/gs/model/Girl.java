package gs.model;

import gs.geometry.Point;
import org.slf4j.LoggerFactory;

public class Girl extends GameObject implements Movable, Tickable {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Girl.class);
    private static final int GIRL_WIDTH = 27;
    private static final int GIRL_HEIGHT = 27;
    private Direction direction = Direction.IDLE;
    private transient int speed = 1;
    private transient int bombCapacity = 1;
    private transient int bombRange = 1;

    public Girl(GameSession session, Point position) {
        super(session, new Point(position.getX() * GameObject.getWidthBox(), position.getY() * GameObject.getWidthBox()),
                "Pawn", GIRL_WIDTH, GIRL_HEIGHT);
        logger.info("New Girl id={}, position={}, session_ID = {}", id, position, session.getId());
    }

    public void incBombCapacity() {
        this.bombCapacity++;
    }

    public void plantBomb() {
        Point bitmapPosition = this.position.convertToBitmapPosition();
        new Bomb(this.session, bitmapPosition, this);
        this.bombCapacity--;
    }

    @Override
    public Point move(int time) {
        switch (direction) {
            case UP:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX(), position.getY() + speed * time);
                setPosition(new Point(position.getX(), position.getY() + speed * time/2));
                break;
            case DOWN:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX(), position.getY() - speed * time);
                setPosition(new Point(position.getX(), position.getY() - speed * time/2));
                break;
            case RIGHT:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX() + speed * time, position.getY());
                setPosition(new Point(position.getX() + speed * time/2, position.getY()));
                break;
            case LEFT:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX() - speed * time, position.getY());
                setPosition(new Point(position.getX() - speed * time/2, position.getY()));
                break;
            default:
                return position;
        }
        return position;
    }

    public Point moveBack(int time) {
        switch (direction) {
            case DOWN:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX(), position.getY() + speed * time);
                setPosition(new Point(position.getX(), position.getY() + speed * time/2));
                setDirection(Direction.IDLE);
                break;
            case UP:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX(), position.getY() - speed * time);
                setPosition(new Point(position.getX(), position.getY() - speed * time/2));
                setDirection(Direction.IDLE);
                break;
            case LEFT:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX() + speed * time, position.getY());
                setPosition(new Point(position.getX() + speed * time/2, position.getY()));
                setDirection(Direction.IDLE);
                break;
            case RIGHT:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX() - speed * time, position.getY());
                setPosition(new Point(position.getX() - speed * time/2, position.getY()));
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
        logger.info("Girl id = {} moved {} ({}, {}) to ({}, {})",
                getId(), direction.name(), oldX, oldY, x, y);
    }

    @Override
    public void tick(int elapsed) {
        move(elapsed);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return this.direction;
    }
}
