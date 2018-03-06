package ru.atom.geometry;

/**
 * Entity that can physically intersect, like flame and player
 */
public interface Collider {
    //boolean equals(Bar o);
    //boolean equals(Point o);
    boolean isColliding(Collider other);
}