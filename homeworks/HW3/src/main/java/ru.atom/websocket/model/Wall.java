package ru.atom.websocket.model;

import ru.atom.geometry.Bar;
import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * Created by BBPax on 06.03.17.
 */
public class Wall extends AbstractGameObject {
    private static final Logger log = LogManager.getLogger(Wall.class);
    final Random random = new Random();

    public Wall(int id, Point position) {
        super(id, position.getX(),position.getY());
        type = "Wood";
        bar = new Bar(new Point(32 * position.getX(), 32 * position.getY()), 32);
        log.info("Wall(id = {}) was created in ( {} ; {} ) with bar {}",
                id, position.getX(), position.getY(), bar.toString());
    }

    public Bonus plantBonus() {
        if ((random.nextDouble() < 0.3)) {  //Вероятность появления бонуса
            Bonus.Type type = null;
            int numType = random.nextInt(3);
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
