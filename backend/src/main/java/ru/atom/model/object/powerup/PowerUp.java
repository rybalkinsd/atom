package ru.atom.model.object.powerup;

import ru.atom.model.object.actor.Pawn;

import java.util.function.Consumer;

/**
 * Created by sergey on 2/11/17.
 */
public enum PowerUp {
    SPEED_UP (pawn -> pawn.addSpeed(0.8)),
    BOMB_POWER (pawn -> pawn.addBombPower(1)),
    BOMB_NUUMBER (pawn -> pawn.addMaxBombs(1));


    private Consumer<Pawn> action;

    PowerUp(Consumer<Pawn> action) {
        this.action = action;
    }

    private void apply(Pawn pawn) {
        action.accept(pawn);
    }
}
