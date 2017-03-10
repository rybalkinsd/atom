package ru.atom.model.input;

import ru.atom.model.object.actor.Pawn;

/**
 * Created by sergey on 2/2/17.
 */
public class PlantBomb implements InputAction {
    @Override
    public void act(Pawn pawn) {
        pawn.setWantPlantBomb(true);
    }
}
