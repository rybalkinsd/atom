package ru.atom.model;

/**
 * Created by pavel on 07.03.17.
 */
public interface Dieable extends GameObject {
    /**
     * Checks if gameObject is dead. If it becomes dead, executes death actions
     * @return true if GameObject is dead
     */
    boolean isDead();
}
