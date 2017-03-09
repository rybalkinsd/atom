package ru.atom.model;

/**
 * GameObjects, that lives for some time and the die
 */
public interface Temporary extends Dieable, Tickable {
    /**
     * @return lifetime in milliseconds
     */
    long getLifetimeMillis();
}
