package utils;

import model.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public interface PlayerPlacer {
    void place(@NotNull Player player);
}
