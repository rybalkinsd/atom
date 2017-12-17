package ru.atom.model;

import ru.atom.geometry.Collider;

public interface Colliding {
    /**
     * @return collider, with which current object is represented
     */
    Collider getCollider();
}
