package ru.atom.model.input;

import ru.atom.model.actor.Pawn;
import ru.atom.util.JsonHelper;
import ru.atom.util.V;

/**
 * Created by sergey on 2/2/17.
 */
public class Move implements InputAction {
    private final V direction;

    Move(V direction) {
        this.direction = direction;
    }

    public static Move from(String json) {
        return JsonHelper.fromJSON(json, Move.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        return direction != null ? direction.equals(move.direction) : move.direction == null;
    }

    @Override
    public int hashCode() {
        return direction != null ? direction.hashCode() : 0;
    }

    public V getDirection() {
        return direction;
    }

    @Override
    public void act(Pawn pawn) {
        pawn.setVelocity(direction);
    }
}
