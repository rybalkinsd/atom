package utils;

import model.Field;
import model.Player;
import model.PlayerCell;
import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public interface PlayerPlacer {
  void place(@NotNull Player player);
}
