package bomber.games.gameobject;

import bomber.games.geometry.Point;
import bomber.games.model.Positionable;
import org.slf4j.LoggerFactory;

public final class Bonus implements Positionable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Bonus.class);

    private final Point position;
    private final int id;
    private final Type type;

    public enum Type {
        Bonus_Bomb, Bonus_Speed, Bonus_Fire
    }

    public Bonus(final int id, final Point position, final Type type) {
        this.id = id;
        this.position = position;
        this.type = type;

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
            Bonus bonus = (Bonus) obj;
            return this.id == bonus.id;
        }
        return false;
    }

    @Override
    public String toString() {
        return "\nBonus: {" +
                "\nid = " + id +
                "\nposition = " + position +
                "\ntype = " + type +
                "\n}";
    }

    public Type getType() {
        return type;
    }
}
