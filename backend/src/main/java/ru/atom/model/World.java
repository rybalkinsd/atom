package ru.atom.model;

import ru.atom.model.actor.Actor;
import ru.atom.model.actor.GameObject;
import ru.atom.model.actor.Tickable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by sergey on 2/2/17.
 */
public class World implements Tickable {
    private static final ThreadLocal<World> universe = new ThreadLocal<>();
    private Collection<GameObject> objects;

    public World(Level level) {
        objects = new ArrayList<>();
        objects.addAll(level.getTiles());
        // fixme unsafe publication
        universe.set(this);
    }

    @Override
    public void tick(long time) {
        objects.stream()
                .filter(x -> x instanceof Actor)
                .map(x ->(Actor) x)
                .forEach(x -> x.tick(time));
    }

    public void register(GameObject object) {
        objects.add(object);
    }

    public static Optional<World> my() {
        return Optional.ofNullable(universe.get());
    }

    public void unregister(GameObject gameObject) {
        objects.remove(gameObject);
    }
}
