package ru.atom.geometry;

/**
 * Entity that can physically intersect, like flame and player
 */
public interface Collider {

    int getLeftDownCornerX();

    int getLeftDownCornerY();

    int getRightUpCornerX();

    int getRightUpCornerY();

    boolean isColliding(Collider other);
}