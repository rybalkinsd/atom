package ru.atom.model;

/**
 * Created by Auerbah on 10.03.2017.
 */
public class UniqueId {
    private static int id = 0;

    public static int getId() {
        return id++;
    }
}
