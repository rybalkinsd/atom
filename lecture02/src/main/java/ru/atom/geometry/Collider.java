package ru.atom.geometry;

/**
 * Entity that can physically intersect, like flame and player
 */
public interface Collider {
    //public static int x = 0, y = 0;
    boolean isColliding(Collider other);
}