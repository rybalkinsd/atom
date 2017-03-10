package ru.atom.model.collision;

import org.jetbrains.annotations.NotNull;

/**
 * Created by sergey on 2/2/17.
 */
public interface Collider {
    default boolean isColliding(@NotNull Collider other) {
        return false;
    }

    @NotNull
    default CollisionProfile getProfile() {
        return CollisionProfile.NONE;
    }
}
