package ru.atom.network.message;

import ru.atom.model.input.Move;

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

//    public <T> T getTopicClass(Class<T> clazz) {
//        return clazz;
//    }

}
