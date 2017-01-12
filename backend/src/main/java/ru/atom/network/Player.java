package ru.atom.network;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sergei-r on 12.01.17.
 */
public class Player {
    private static final IdGenerator idGenerator = new IdGenerator();

    @NotNull
    private final String name;
    private final int id;

    public Player(@NotNull String name) {
        this.id = idGenerator.next();
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return id == player.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    private static class IdGenerator {
        private final AtomicInteger current = new AtomicInteger(0);
        int next() {
            return current.getAndIncrement();
        }
    }
}
