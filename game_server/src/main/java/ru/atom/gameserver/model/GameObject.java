package ru.atom.gameserver.model;

import ru.atom.gameserver.geometry.Bar;
import ru.atom.gameserver.geometry.Point;

/**
 * Created by Alexandr on 05.12.2017.
 */
public interface GameObject {

    int getId();

    Point getPosition();

    Bar getBar();

    void setBar(Bar newBar);

    void calculateBar();
}
