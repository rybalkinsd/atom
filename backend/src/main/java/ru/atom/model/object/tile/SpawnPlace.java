package ru.atom.model.object.tile;

import ru.atom.util.V;

/**
 * Created by sergey on 2/9/17.
 */
public class SpawnPlace extends Tile {

    public SpawnPlace(V position) {
        super(position);
    }

    @Override
    public Character getSimpleMapIcon() {
        return 'S';
    }
}
