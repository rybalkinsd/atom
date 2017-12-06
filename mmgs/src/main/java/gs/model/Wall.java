package gs.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import gs.geometry.Point;

public class Wall extends Field implements Positionable {
    private static final Logger log = LogManager.getLogger(Wall.class);

    private final int id;
    private Point point;

    public enum Type {
        Wood, Wall, Grass
    }

    private Type type;

    public Wall(int x, int y, Type type, int id) {
        super(x, y);
        this.id = id;
        this.type = type;
        this.point = getPosition();
        log.info("Wallid = " + id + "; " + "Wall place = (" + point.getX() + "," +
                point.getY() + ")" + "; " + "Type? = " + type);
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public String toJson() {
        Point pos = getPosition();
        String obj = "{\"type\":\"" + type.name() + "\",\"id\":" + this.getId() +
                ",\"position\":{\"x\":" + pos.getX() + ",\"y\":" + pos.getY() + "}}";
        return obj;
    }
}
