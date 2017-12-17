package bomber.games.gameobject;

import bomber.games.geometry.Point;
import bomber.games.model.Positionable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.LoggerFactory;
import bomber.games.model.Tickable;


public final class Explosion implements Tickable, Positionable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Bomb.class);


    private final Point position;
    private final int id;
    private final String type = "Fire";

    @JsonIgnore
    private boolean alive = true;

    @JsonIgnore
    private int lifeTime = 1000;


    public Explosion(int id, final Point position) {
        this.id = id;
        this.position = position;


    }

    @Override
    public void tick(final long elapsed) {
        lifeTime -= elapsed;
        log.info("lifeTime " + lifeTime);
        if (lifeTime <= 0)
            alive = false;
        log.info("alive " + alive);
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
        else if (obj instanceof Explosion) {
            Explosion explosion = (Explosion) obj;
            return this.id == explosion.id;
        }
        return false;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public String toString() {
        return "\nExplosion: {" +
                "\nid = " + id +
                "\nposition = " + position +
                "\nlifeTime = " + lifeTime +
                "\n}";
    }


    public int getLifeTime() {
        return this.lifeTime;
    }

    public String getType() {
        return type;
    }
}
