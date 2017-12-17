package bomber.games.gameobject;


import bomber.games.geometry.Point;
import bomber.games.model.Positionable;
import org.slf4j.LoggerFactory;

public final class Box implements Positionable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Box.class);
    private final Point position;
    private final int id;
    private final String type = "Box";


    public Box(final int id, final Point position) {
        this.id = id;
        this.position = position;
        log.info("New Box: id={},  id={}, position({}, {})\n", position.getX(), position.getY());
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
        return (int) this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        else if (obj instanceof Player) {
            Box box = (Box) obj;
            return this.id == box.id;
        }
        return false;
    }

    @Override
    public String toString() {
        return "\nBox: {" +
                "\nid = " + id +
                "\nposition = " + position +
                "\n}";
    }
}
