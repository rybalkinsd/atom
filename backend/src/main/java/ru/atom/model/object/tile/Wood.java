package ru.atom.model.object.tile;

import org.jetbrains.annotations.NotNull;
import ru.atom.model.collision.CollisionProfile;
import ru.atom.util.V;

/**
 * Created by sergey on 2/8/17.
 */
public class Wood extends Tile {

    public Wood(V position) {
        super(position);
    }

    @Override
    @NotNull
    public CollisionProfile getProfile() {
        return CollisionProfile.STATIC;
    }

    @Override
    public Character getSimpleMapIcon() {
        return 'o';
    }
}
