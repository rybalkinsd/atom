package ru.atom.network;

import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.atom.model.actor.Pawn;
import ru.atom.model.input.InputAction;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sergei-r on 12.01.17.
 */
public class Player {
    private static final IdGenerator idGenerator = new IdGenerator();

    private final int id;

    private final String name;
    private final Session session;

    @Nullable
    private Pawn pawn;

    public Player(@NotNull String name, @NotNull Session session) {
        this.id = idGenerator.next();
        this.name = name;
        this.session = session;
    }

    private static class IdGenerator {
        private final AtomicInteger current = new AtomicInteger(0);
        int next() {
            return current.getAndIncrement();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return id == player.id;
    }

    public void consumeInput(@NotNull InputAction action) {
        pawn.addInput(action);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @NotNull
    public Session getSession() {
        return session;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setPawn(@NotNull Pawn pawn) {
        this.pawn = pawn;
    }
}
