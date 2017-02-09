package ru.atom.model.actor;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.atom.model.World;
import ru.atom.model.collision.Collider;
import ru.atom.util.V;

/**
 * Created by sergey on 2/8/17.
 */
public abstract class GameObject implements Collider {
    protected V position;

    public V getPosition() {
        return position;
    }

    public void setPosition(V position) {
        this.position = position;
    }

    @JsonProperty("type")
    private String getClassName() {
        return getClass().getSimpleName();
    }

    protected void postConstruct() {
        World.my().ifPresent(world -> world.register(this));
    }

    protected void destroy() {
        World.my().ifPresent(world -> world.unregister(this));
    }
}
