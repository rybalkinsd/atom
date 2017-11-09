package ru.atom.model;



/**
 * Any entity of game mechanics
 */
public interface GameObject extends Positionable {
    /**
     * Unique id
     */
    int getId();
}