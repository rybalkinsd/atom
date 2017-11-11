package ru.atom.model;

public class Field extends AbstractGameObject {

    private static final int width = 25;
    private static final int height = 25;

    public Field() {
        this(0, 0);
    }

    public Field(int x, int y) {
        super(x, y);
    }
}