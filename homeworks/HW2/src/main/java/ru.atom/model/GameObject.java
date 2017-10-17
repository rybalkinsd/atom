package ru.atom.model;

import ru.atom.geometry.Point;

/**
 * Any entity of game mechanics
 */
public interface GameObject extends Positionable {
    /**
     * Unique id
     */
    int getId();
}