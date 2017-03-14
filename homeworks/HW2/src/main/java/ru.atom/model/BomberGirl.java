package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

/**
 * Created by Fella on 14.03.2017.
 */
public class BomberGirl implements Movable {
    private static final Logger log = LogManager.getLogger(BomberGirl.class);
    private Point position;
    private final int id;
    private int speed;
    private long lifetame;
    private int powerBomb;
    int numberBomb;

    public BomberGirl(int x, int y) {
        this.position = new Point(x, y);
        this.id = GameSession.createId();
        speed = 1;
        lifetame = 0L;
        powerBomb = 3;
        numberBomb = 1;
        log.info("New BomberGirl. id=" + id + ", position=(" + x + ";" + y + ")");
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void tick(long elapsed) {
        lifetame += elapsed;
        log.info("tic-tac");

    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public Point move(Direction direction) {
        switch (direction) {
            case UP:
                position = new Point(position.getX(), position.getY() + speed);
                tick(1L);
                break;
            case DOWN:
                position = new Point(position.getX(), position.getY() - speed);
                tick(1L);
                break;
            case RIGHT:
                position = new Point(position.getX() + speed, position.getY());
                tick(1L);
                break;
            case LEFT:
                position = new Point(position.getX() - speed, position.getY());
                tick(1L);
                break;
            case IDLE:
                tick(1L);
                break;
            default: throw new IllegalArgumentException();
        }
        return position;
    }

    public Bomb createBomb() {
        return new Bomb(position.getX(), position.getY(), powerBomb);
    }

    public boolean isColliderNishtyaki(Nishtyaki bonus) {
        if (position == bonus.getPosition()) {
            switch (bonus.type) {
                case NUMBER_BOMB:
                    numberBomb++;
                    break;
                case POWER:
                    powerBomb++;
                    break;
                case SPEED:
                    speed++;
                    break;
                default: throw new IllegalArgumentException();
            }
            return true;
        } else return false;
    }
}
