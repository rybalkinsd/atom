package ru.atom.model.listners;

import ru.atom.geometry.GeomObject;
import ru.atom.geometry.IntersectionParams;
import ru.atom.geometry.Point;
import ru.atom.model.FormedGameObject;
import ru.atom.model.MovableFormedGameObject;

public interface MoveEventListener {
    Point getMoveRecom(FormedGameObject newForm, MovableFormedGameObject oldObject);

    void handleMoveEvent(FormedGameObject oldForm, MovableFormedGameObject newObject);
}
