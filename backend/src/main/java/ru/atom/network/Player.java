package ru.atom.network;

import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import ru.atom.model.actor.Pawn;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sergei-r on 12.01.17.
 */
public class Player {
    private static final IdGenerator idGenerator = new IdGenerator();

    private final int id;

    @NotNull
    private final String name;
    private final Session session;

    private Pawn pawn;

    public Player(@NotNull String name, Session session) {
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

    @Override
    public int hashCode() {
        return id;
    }

    public Session getSession() {
        return session;
    }

    public @NotNull String getName() {
        return name;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }
}
