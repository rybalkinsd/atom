package ru.atom.model.actor;

/**
 * Created by sergei-r on 11.01.17.
 */
public class Pawn extends Actor {
    private boolean wantPlantBomb = false;

    public void plantBomb() {
        assert wantPlantBomb;
        // action
        wantPlantBomb = false;
    }

}
