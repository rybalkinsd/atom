package gameobjects;

import geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Wall extends Field implements Positionable {
    private int x;
    private int y;
    private int id;

    public Wall(int x, int y) {
        super(x, y);
        this.id = getId();
        log.info("New wall with id {}", id);
    }

    private final Logger log = LogManager.getLogger(Wall.class);

    public String toJson() {
        Point pos = getPosition();
        String json = "{\"type\":\"" + this.getClass().getSimpleName() + "\",\"id\":" +
                this.getId() + ",\"position\":{\"x\":" + pos.getX() + ",\"y\":" + pos.getY() + "}}";
        return json;
    }


}
