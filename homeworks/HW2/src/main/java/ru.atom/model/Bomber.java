package  ru.atom.model;

import ru.atom.geometry.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bomber implements Movable {

    private static final Logger log = LogManager.getLogger(Bomb.class);
    private int id;
    private int x;
    private int y;
    private float velocity = 2;
    private int bombStrength = 1;
    private int bombsMax = 1;

    public Bomber(int id, Point position) {
        this.id = id;
        x = position.getX();
        y = position.getY();
        log.info("Bomber id={} x={} y={} created",
                id, x, y);
    }

    public Bomber(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        log.info("Bomber id={} x={} y={} created",
                id, x, y);
    }

    public int getBombStrength() {
        return bombStrength;
    }

    public int getBombsMax() {
        return bombsMax;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Point getPosition() {
        return new Point(x,y);
    }

    @Override
    public Point move(Direction direction, long time) {
        if (direction == Direction.UP) {
            y += velocity * time;
        } else if (direction == Direction.DOWN) {
            y -= velocity * time;
        } else if (direction == Direction.RIGHT) {
            x += velocity * time;
        } else if (direction == Direction.LEFT) {
            x -= velocity * time;
        }
        return getPosition();
    }

    public void boost() {
        velocity += 0.8;
    }

    public void incBombStrength() {
        bombStrength++;
    }

    public void addBomb() {
        bombsMax++;
    }

    public void tick(long elapsed) {
    }
}