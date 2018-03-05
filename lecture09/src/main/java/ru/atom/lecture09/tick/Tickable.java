package ru.atom.lecture09.tick;

/**
 * Any game object that changes with time
 */
public interface Tickable {
    /**
     * Applies changes to game objects that happen after elapsed time
     */
    void tick(long elapsed);
}
