package ru.atom.geometry;

/**
 * Entity that can physically intersect, like flame and player
 */
@SuppressWarnings("ALL")
public interface Collider {
    boolean isColliding(Collider other);
}