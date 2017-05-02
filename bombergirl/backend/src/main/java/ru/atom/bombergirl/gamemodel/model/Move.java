package ru.atom.bombergirl.gamemodel.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ikozin on 01.05.17.
 */
public class Move implements Action {
    private Movable.Direction direction;

    public Move(@JsonProperty("direction") Movable.Direction direction) {
        this.direction = direction;
    }

    @Override
    public void act(Pawn pawn) {
        pawn.move(direction);
    }
}
