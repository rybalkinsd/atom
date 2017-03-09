package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Bonus extends AbstractGameObject implements Positionable {
    private static final Logger logger = LogManager.getLogger(Bonus.class);

    public enum BonusType {
        SPEED,
        BOMB,
        FIRE
    }

    private BonusType type;

    public Bonus(int x, int y, BonusType type) {
        super(x, y);
        this.type = type;
        logger.info("new Bonus! id = {} x = {} y = {} type {}", getId(), x, y, type);
    }

}
