package ru.atom.geometry;

import java.util.Objects;

/**
 * Entity that can physically intersect, like flame and player
 */
public interface Collider {
    public boolean isColliding(Collider other);

    public boolean equals(Object other);
}