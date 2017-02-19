package ru.atom.model.object.powerup;

import ru.atom.model.object.GameObject;
import ru.atom.model.object.actor.Pawn;

/**
 * Created by sergey on 2/11/17.
 */
public abstract class PowerUp1 extends GameObject {
    public abstract void apply(Pawn pawn);
}
