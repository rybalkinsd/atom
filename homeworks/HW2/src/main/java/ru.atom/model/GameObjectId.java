package ru.atom.model;

/**
 * Created by Юля on 10.03.2017.
 */
public class GameObjectId implements GameObject {
    private static int count = 0;
    private int id = count++;


    @Override
    public int getId() {
        return id;
    }
}
