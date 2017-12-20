package ru.atom.gameserver.geometry;

/**
 * Created by Alexandr on 05.12.2017.
 */
public interface Collider {
    boolean isColliding(Collider other);
}
