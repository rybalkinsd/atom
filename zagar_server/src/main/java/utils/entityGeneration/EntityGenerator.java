package utils.entityGeneration;

import model.Field;
import model.GameConstants;
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
    private boolean firstRun = true;
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
     * Calls {@see generate()} method only if {@see idleDuration} greater than {@see GameConstants.GENERATORS_PERIOD}
     * @param elapsed time interval
     */
    @Override
    public void tick(@NotNull Duration elapsed) {
        //do work only when idleDuration greater preset value
        if (idleDuration.compareTo(GameConstants.GENERATORS_PERIOD)>0) {
            idleDuration = Duration.ZERO;
        } else if (firstRun) {
            firstRun = false;
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
