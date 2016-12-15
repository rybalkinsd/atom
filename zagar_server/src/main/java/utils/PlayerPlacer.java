package utils;

import model.Field;
import model.Player;
import org.jetbrains.annotations.NotNull;

public interface PlayerPlacer {
  void place(@NotNull Player player);
  void setField(Field field);
}