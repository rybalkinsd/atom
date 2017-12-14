package ru.atom.model;

import ru.atom.geometry.GeomObject;
import ru.atom.geometry.IntersectionParams;

public interface MoveEventListener {

    boolean getMovePermission(FormedGameObject geomObject, MovableFormedGameObject gameObject);
    void handleMoveEvent(FormedGameObject geomObject, MovableFormedGameObject gameObject);
}
