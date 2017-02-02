package ru.atom.network.message;

/**
 * Created by sergey on 2/2/17.
 */
public enum Topic {
    MOVE (Move.class.getName(), Move.class),
    PLANT_BOMB ("", null);

    private String name;
    private Class<?> clazz;

    Topic(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public Class<?> getTopicClass() {

    }

}
