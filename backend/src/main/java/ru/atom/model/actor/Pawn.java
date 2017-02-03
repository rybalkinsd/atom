package ru.atom.model.actor;

import ru.atom.model.input.InputAction;
import ru.atom.util.V;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by sergei-r on 11.01.17.
 */
public class Pawn extends Actor {
    private boolean wantPlantBomb = false;
    private Collection<InputAction> inbox = new ArrayList<>();

    public Pawn(V position) {
        setPosition(position);
        setVelocity(V.ZERO);
    }

    @Override
    public void tick(long time) {
        inbox.forEach(x -> x.act(this));
        super.tick(time);
        inbox.clear();
    }

    public void plantBomb() {
        assert wantPlantBomb;
        // action
        wantPlantBomb = false;
    }

    public void addInput(InputAction inputAction) {
        inbox.add(inputAction);
    }

}
