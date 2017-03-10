package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Player extends CommonGameObject implements Movable {
    private static final Logger logger = LogManager.getlogger(Player.class);

    private int bombPower;
    private int explosionRange;
    private int speed;
    private int timeMillis;

    public Player(int x, int y) {
        super(x, y);
        bombPower = 1;
        explosionRange = 3;
        speed = 1;
        logger.info("new player id = {} x = {} y = {} speed = {}", getId(), x, y, speed, );

    }

    public Player(int x, int y, int speed, int bombPower, int explosionRange) {
        super(x, y);
        if (speed <= 0) {
            logger.error("invalid speed");
            throw new IllegalArgumentException;
        }
        this.speed = speed;
        this.bombPower = bombPower;
        this.explosionRange = explosionRange;
        logger.info("new player id = {} x = {} y = {} speed = {}", getId(), x, y, speed, );
    }

    @Override
    public void tick(long elapsed) {
        timeMillis += elapsed;
    }

    @Override
    public Point move(Direction direction) {
        return direction.move(this.position, this.velocity);
    }

}