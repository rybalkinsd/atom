package ru.atom.network.message;

import ru.atom.util.V;

/**
 * Created by sergey on 2/2/17.
 */
public class Move {
    private final V direction;

    Move(V direction) {
        this.direction = direction;
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
}
