package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class BomberGirl implements Movable {

    private static final Logger log = LogManager.getLogger(BomberGirl.class);

    private int speed = 5;
    private int bomb_counter = 1; //на будущее
    private int bomb_range = 1; //на будущее

    public static Long id = 10000000000L;
    private Point position;

    public BomberGirl(Point position) {

        this.id += 1;
        this.position = position;
        log.info("New Girl id={}, position={}", id, position);
    }

    @Override
    public Point move(Direction direction, long time) {
        switch (direction){
            case UP: this.position = new Point(getPosition().getX(), getPosition().getY() + (int)time);
                break;
            case DOWN: this.position = new Point(getPosition().getX(), getPosition().getY() - (int)time);
                break;
            case RIGHT: this.position = new Point(getPosition().getX() + (int)time, getPosition().getY());
                break;
            case LEFT: this.position = new Point(getPosition().getX() - (int)time, getPosition().getY());
                break;
            case IDLE: this.position = new Point(getPosition().getX(), getPosition().getY());
                break;
        }

        return this.position;
    }

    @Override
    public Point getPosition() {

        return this.position;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void tick(long elapsed) {
        log.info("tick {}",elapsed);
    }

}