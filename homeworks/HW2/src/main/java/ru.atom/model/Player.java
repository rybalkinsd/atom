package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Player implements Movable{

    private static final Logger log = LogManager.getLogger(Player.class);
    private final int id;

    private Point position;
    private long lifeTime;
    private int speed;
    private int maxBombAmount;
    private int explosionRange;

    public Player(final Point position) {
        this.position = position;
        this.speed = 1;
        this.maxBombAmount = 1;
        this.explosionRange = 1;
        this.id = GameSession.nextId();
        this.lifeTime = 0;
        log.info("Player: id={}, position({}, {})\n", id, position.getX(), position.getY()) ;
    }

    @Override
    public Point move(Direction direction, long time) {
        int distance =(int)(time * speed);
        switch(direction) {
            case UP:
                position = new Point(position.getX(), position.getY() + distance);
                break;
            case DOWN:
                position = new Point(position.getX(), position.getY() - distance);
                break;
            case LEFT:
                position = new Point(position.getX() - distance, position.getY());
                break;
            case RIGHT:
                position = new Point(position.getX() + distance, position.getY());
                break;
            case IDLE:
                position = new Point(position.getX(), position.getY());
                break;
            default:
                throw new IllegalArgumentException("Wrong direction");
        }
        return position;
    }

    public int getExplosionRange() {
        return explosionRange;
    }

    @Override
    public void tick(long elapsed) {
        lifeTime += elapsed;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }
}
