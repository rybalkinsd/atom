package ru.atom.model.walls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.Point;
import ru.atom.model.GameSession;
import ru.atom.model.Positionable;

/**
 * Created by IGIntellectual on 10.03.2017.
 */
public class UnbreakableWall implements Positionable {
    private static final Logger LOG = LogManager.getLogger(StandartWall.class);

    private Point position;
    private int id;

    public UnbreakableWall(int x, int y) {
        this.position = new Point(x, y);
        this.id = GameSession.getId();
        LOG.info("UnbreakableWall ( id = {} ) was created in ( {};{} ) ",
                this.id, position.getX(), position.getY());
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
