package ru.atom.model.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.atom.model.World;
import ru.atom.model.collision.Collider;
import ru.atom.util.V;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * Created by sergey on 2/8/17.
 */
public abstract class GameObject implements Collider {
    private Collection<Consumer<? super GameObject>> destroySubscriptions = new ArrayList<>();
    protected V position;

    public V getPosition() {
        return position;
    }

    public void setPosition(V position) {
        this.position = position;
    }

    protected void postConstruct() {
        World.my().ifPresent(world -> world.register(this));
    }

    protected void destroy() {
        destroySubscriptions.forEach(consumer -> consumer.accept(this));
        World.my().ifPresent(world -> world.unregister(this));
    }

    public void onDestroy(Consumer<? super GameObject> callback) {
        destroySubscriptions.add(callback);
    }

    @JsonProperty("type")
    private String getClassName() {
        return getClass().getSimpleName();
    }
}
