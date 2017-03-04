package ru.atom.model;

/**
 * GameObjects, that lives for some time and the die
 */
public interface Temporary extends Tickable, GameObject {
    /**
     * @return lifetime in milliseconds
     */
    long getLifetimeMillis();

    /**
     * Checks if gameObject is dead. If it becomes dead, executes death actions
     * @return true if GameObject is dead
     */
    boolean isDead();
}
