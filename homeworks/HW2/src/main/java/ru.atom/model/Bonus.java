package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;

public class Bonus implements Tickable, Positionable {
    private int id;
    private boolean isExisting;
    private Point position;

    public enum BonusType {
        bombStrength, bombQuantity, playerVelocity
    }

    private BonusType bonusType;
    final Logger log = LogManager.getLogger(GameSession.class);

    public Bonus(int id, Point position, BonusType bonusType) {
        isExisting = true;
        this.id = id;
        this.bonusType = bonusType;
        this.position = position;
        log.info("Bonus(" + bonusType + ") with ID(" + id + ") was created on point ("
                + position.getX() + "," + position.getY() + ")");
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
