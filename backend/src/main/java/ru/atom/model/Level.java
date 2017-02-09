package ru.atom.model;

import org.jetbrains.annotations.NotNull;
import ru.atom.model.actor.tile.Tile;
import ru.atom.util.V;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sergey on 2/8/17.
 */
public enum Level {
    STANDARD(
            new ArrayList<>(), Collections.singletonList(V.of(1, 6))
    );

    private List<Tile> tiles;
    private List<V> spawnPlaces;

    Level(@NotNull List<Tile> tiles, @NotNull List<V> spawnPlaces) {
        this.tiles = tiles;
        this.spawnPlaces = spawnPlaces;
    }

    public int getMaxPlayers() {
        return spawnPlaces.size();
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<V> getSpawnPlaces() {
        return spawnPlaces;
    }
}
