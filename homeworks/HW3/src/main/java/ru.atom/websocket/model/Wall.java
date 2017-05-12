package ru.atom.websocket.model;

import ru.atom.geometry.Bar;
import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by BBPax on 06.03.17.
 */
public class Wall extends AbstractGameObject {
    private static final Logger log = LogManager.getLogger(Wall.class);

    public Wall(int id, Point position) {
        super(id, position.getX(),position.getY());
        type = "Wood";
        bar = new Bar(new Point(32 * position.getX(), 32 * position.getY()), 32);
        log.info("Wall(id = {}) was created in ( {} ; {} ) with bar {}",
                id, position.getX(), position.getY(), bar.toString());
    }

    protected Wall setDead() {
        // TODO: 12.05.17   here Bonus may be appeared???
        return this;
    }
}
