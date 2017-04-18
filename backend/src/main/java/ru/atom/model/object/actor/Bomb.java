package ru.atom.model.object.actor;

import ru.atom.util.V;

/**
 * Created by sergey on 2/10/17.
 */
public class Bomb extends Actor {

    private Bomb(V position) {
        setPosition(position);
    }

    public static Bomb create(V position) {
        Bomb instance = new Bomb(position);
        instance.postConstruct();
        return instance;
    }
}
