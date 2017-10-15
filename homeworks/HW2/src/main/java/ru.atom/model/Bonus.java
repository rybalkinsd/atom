package  ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Bonus implements Positionable {

    protected static final Logger log = LogManager.getLogger(Bonus.class);
    protected int id;
    protected Point position;

    public Bonus(int id, Point position) {
        this.id = id;
        this.position = position;
    }

    public Bonus(int id, int x, int y) {
        this.id = id;
        position = new Point(x,y);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return position;
    }
}