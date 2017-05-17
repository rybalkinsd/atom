package ru.atom.websocket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.atom.geometry.Bar;
import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import static ru.atom.WorkWithProperties.getProperties;

/**
 * Created by BBPax on 06.03.17.
 */
public class Wall extends AbstractGameObject {
    private static final Logger log = LogManager.getLogger(Wall.class);
    public static final double PROBABILITY_OF_BONUS = Double.valueOf(getProperties()
            .getProperty("PROBABILITY_OF_BONUS"));
    public static final int AMOUNT_OF_TYPE_BONUSES = Integer.valueOf(getProperties()
            .getProperty("AMOUNT_OF_TYPE_BONUSES"));
    @JsonIgnore
    final Random random = new Random();

    public Wall(int id, Point position) {
        super(id, position.getX(),position.getY());
        type = "Wood";
        bar = new Bar(new Point(BAR_SIZE * position.getX(), BAR_SIZE * position.getY()), BAR_SIZE);
        log.info("Wall(id = {}) was created in ( {} ; {} ) with bar {}",
                id, position.getX(), position.getY(), bar.toString());
    }

    public Bonus plantBonus() {
        if ((random.nextDouble() < PROBABILITY_OF_BONUS)) {  //Вероятность появления бонуса
            Bonus.Type type = null;
            int numType = random.nextInt(AMOUNT_OF_TYPE_BONUSES);
            if (numType == 0) type = Bonus.Type.SPEED;
            else if (numType == 1) type = Bonus.Type.BOMB;
            else if (numType == 2)type = Bonus.Type.RANGE;
            return new Bonus(0, position, type);
        } else {
            return null;
        }
    }

    protected Wall setDead() {
        return this;
    }
}
