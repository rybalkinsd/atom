package ru.atom.gameserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.atom.gameserver.geometry.Point;

public class Wood extends AbstractGameObject implements Static {

    @JsonIgnore
    private Buff.BuffType buffType = null;

    public Wood(int id, Point position) {
        super(id, position);
    }

    public boolean containsBuff() {
        return buffType != null;
    }

    public Buff.BuffType getBuffType() {
        return buffType;
    }

    public void setBuffType(Buff.BuffType buffType) {
        this.buffType = buffType;
    }
}
