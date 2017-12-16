package gs.model;

import gs.geometry.Point;
import org.slf4j.LoggerFactory;

public class Bonus extends GameObject{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Bonus.class);
    private static final int BONUS_WIDTH = 28;
    private static final int BONUS_HEIGHT = 28;
    private final BonusType bonusType;
    enum BonusType {
        SPEED,
        RANGE,
        BOMBS
    }

    public Bonus(GameSession session, Point position, BonusType bonusType) {
        super(session, new Point(position.getX() * GameObject.getWidthBox(),
                        position.getY() * GameObject.getWidthBox()),
                "Bonus", BONUS_WIDTH, BONUS_HEIGHT);
        this.bonusType = bonusType;
        logger.info("New Bonus id={}, position={}, type = {}, session_ID = {}", id, position, bonusType, session.getId());
    }
}
