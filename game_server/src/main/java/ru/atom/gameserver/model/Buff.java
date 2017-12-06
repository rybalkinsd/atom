package ru.atom.gameserver.model;

import ru.atom.gameserver.geometry.Point;

public class Buff extends AbstractGameObject {

    private final BuffType type;

    public Buff(int id, Point position, BuffType type) {
        super(id, position);
        this.type = type;
    }

    public BuffType getType() {
        return type;
    }

    public enum BuffType {
        VELOCITY,    // speed of player
        POWER,    // max size of explosion line
        CAPACITY; // max numbers of bombs

        public void affect() {
            switch (this) {
                case VELOCITY:
                    break;
                case POWER:
                    break;
                case CAPACITY:
                    break;
                default:
                    break;
            }
        }
    }
}
