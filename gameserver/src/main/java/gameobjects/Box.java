package gameobjects;

import geometry.Bar;
import geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;


public class Box extends Field implements Positionable, Tickable, Comparable {
    private final Logger log = LogManager.getLogger(Box.class);
    private int x;
    private int y;
    private int id;
    private boolean alive;
    private GameSession gameSession;

    public Box(int x, int y, GameSession gameSession) {
        super(x, y);
        this.id = getId();
        this.gameSession = gameSession;
        this.alive = true;
        log.info("New box with id {}", id);
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return 0;
    }


    public Bar getBar() {
        return new Bar(x, y, x + 28, y + 28);
    }

    public void tick(long elapsed) {
        //log.info("box {} tick", id);
        if (alive) {
            if (!gameSession.getCellFromGameArea(getPosition().getX(), getPosition().getY())
                    .getState().contains(State.BOX))
                alive = false;
        } else gameSession.removeGameObject(this);
    }

    public String toJson() {
        Point pos = getPosition();
        String json = "{\"type\":\"Wood\",\"id\":" +
                this.getId() + ",\"position\":{\"x\":" + pos.getX() + ",\"y\":" + pos.getY() + "}}";
        return json;
    }
}
