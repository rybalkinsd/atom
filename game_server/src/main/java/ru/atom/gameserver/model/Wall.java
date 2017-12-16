package ru.atom.gameserver.model;


import ru.atom.gameserver.geometry.Point;

/**
 * Created by Alexandr on 05.12.2017.
 */
public class Wall extends AbstractGameObject implements Static {

    public Wall(int id, Point position) {
        super(id, position);
    }

}
