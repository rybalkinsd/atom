package bomber.games.gameobject;

import bomber.games.geometry.Point;
import bomber.games.model.Positionable;
import org.slf4j.LoggerFactory;


public final class Wall implements Positionable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Wall.class);


    private final Point position;
    private final int id;
    private final String type = "Wall";

    public Wall(final int id, final Point position) {
        this.id = id;
        this.position = position;
        log.info("New Wall: id={},  id={}, position({}, {})", id, position.getX(), position.getY());
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
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj instanceof Player) {
            Wall wall = (Wall) obj;
            return this.id == wall.id;
        }
        return false;
    }

    @Override
    public String toString() {
        return "\nWall: {" +
                "\nid = " + id +
                "\nposition" + position +
                "\n}";
    }
}
