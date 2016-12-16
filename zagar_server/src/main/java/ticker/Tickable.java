package ticker;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;

/**
 * Created by apomosov on 14.05.16.
 *
 * Interface for objects which performs actions with given interval
 */
public interface Tickable {
    /**
     * Perform an action
     * @param elapsed time interval
     */
    void tick(@NotNull Duration elapsed);
}
