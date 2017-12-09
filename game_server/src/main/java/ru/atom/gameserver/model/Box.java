package ru.atom.gameserver.model;

import ru.atom.gameserver.geometry.Point;

public class Box extends AbstractGameObject {

    private Buff.BuffType buffType = null;

    public Box(int id, Point position) {
        super(id, position);
    }

    public void setBuff(Buff.BuffType buffType) {
        this.buffType = buffType;
    }

    public boolean containsBuff() {
        return buffType != null;
    }

    public Buff.BuffType getBuffType() {
        return buffType;
    }

    public Buff getBuff() {
        if (buffType == null) {
            throw new NullPointerException("the buffType field is null");
        }
        return new Buff(getId(), getPosition(), buffType); // FIXME: needs to generate new id!!!
    }

}
