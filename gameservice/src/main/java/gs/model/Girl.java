package gs.model;

import gs.geometry.Point;
import org.slf4j.LoggerFactory;

public class Girl extends GameObject implements Movable {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Girl.class);
    private static final int GIRL_WIDTH = 48;
    private static final int GIRL_HEIGHT = 48;

    private int speed = 30;
    private int bombCapacity = 1;
    private int bombRange = 1;

    public Girl(GameSession session, Point position) {
        super(session, position, GIRL_WIDTH, GIRL_HEIGHT);
        logger.info("New Girl id={}, position={}, session_ID = {}", id, position, session.getId());
    }

    public int getSpeed() {
        return speed;
    }

    public int getBombCapacity() {
        return bombCapacity;
    }

    public int getBombRange() {
        return bombRange;
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
    public Point move(Direction direction, int time) {
        switch (direction) {
            case UP:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX(), position.getY() + speed * time);
                setPosition(new Point(position.getX(), position.getY() + speed * time));
                break;
            case DOWN:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX(), position.getY() - speed * time);
                setPosition(new Point(position.getX(), position.getY() - speed * time));
                break;
            case RIGHT:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX() + speed * time, position.getY());
                setPosition(new Point(position.getX() + speed * time, position.getY()));
                break;
            case LEFT:
                moveLog(direction, position.getX(), position.getY(),
                        position.getX() - speed * time, position.getY());
                setPosition(new Point(position.getX() - speed * time, position.getY()));
                break;
            default:
                return position;
        }
        return position;
    }

    @Override
    public void tick(int elapsed) {
        throw new UnsupportedOperationException();
    }

    public void moveLog(Direction direction, int oldX, int oldY, int x, int y) {
        logger.info("Girl id = {} moved {} ({}, {}) to ({}, {})",
                getId(), direction.name(), oldX, oldY, x, y);
    }
}