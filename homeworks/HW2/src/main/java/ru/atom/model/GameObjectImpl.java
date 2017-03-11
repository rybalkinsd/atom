package ru.atom.model;

public class GameObjectImpl implements GameObject {

    private static int counter = 0;

    private int id = counter++;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
