package ru.atom.model.actor.tile;

import org.jetbrains.annotations.NotNull;
import ru.atom.model.collision.CollisionProfile;

/**
 * Created by sergey on 2/8/17.
 */
public class Wall extends Tile {
    @NotNull
    @Override
    public CollisionProfile getProfile() {
        return CollisionProfile.STATIC;
    }
}
