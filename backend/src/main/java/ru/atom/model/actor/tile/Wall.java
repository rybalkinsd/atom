package ru.atom.model.actor.tile;

import org.jetbrains.annotations.NotNull;
import ru.atom.model.collision.CollisionProfile;
import ru.atom.util.V;

/**
 * Created by sergey on 2/8/17.
 */
public class Wall extends Tile {

    public Wall(V position) {
        super(position);
    }

    @NotNull
    @Override
    public CollisionProfile getProfile() {
        return CollisionProfile.STATIC;
    }

    @Override
    public Character getSimpleMapIcon() {
        return 'X';
    }
}
