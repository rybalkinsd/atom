package utils.entityGeneration;

import model.Field;
import org.jetbrains.annotations.NotNull;
import ticker.Tickable;

import java.time.Duration;

/**
 * Created by xakep666 on 08.12.16.
 * <p>
 * Base class for entity generators
 */
public abstract class EntityGenerator implements Tickable {
    @NotNull
    private final Field field;
    @NotNull
    private Duration idleDuration = Duration.ZERO;

    /**
     * Base constructor
     * @param field field where generator will be generate objects
     */
    EntityGenerator(@NotNull Field field) {
        this.field = field;
    }

    @NotNull
    protected Field getField() {
        return field;
    }

    /**
     * Method contains generator logic
     * @param elapsed tick time interval
     */
    abstract void generate(@NotNull Duration elapsed);

    /**
     * Calls {@see generate()} method only if {@see idleDuration} greater than 1 second
     * @param elapsed time interval
     */
    @Override
    public void tick(@NotNull Duration elapsed) {
        //do work only when idleDuration greater than 1 second
        if (idleDuration.toMillis() >= 1000) {
            idleDuration = Duration.ZERO;
        } else {
            idleDuration = idleDuration.plus(elapsed);
            return;
        }
        generate(elapsed);
    }

    @NotNull
    protected Duration getIdleDuration() {
        return idleDuration;
    }
}
