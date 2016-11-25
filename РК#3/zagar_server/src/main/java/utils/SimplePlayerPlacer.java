package utils;

import model.Field;
import model.Player;
import model.PlayerCell;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * @author apomosov
 */
public class SimplePlayerPlacer implements PlayerPlacer {
  @NotNull
  private final Field field;

  public SimplePlayerPlacer(@NotNull Field field) {
    this.field = field;
  }

  @Override
  public void place(@NotNull Player player) {
    assert(player.getCells().size() == 1);
    Random random = new Random();
    for (PlayerCell playerCell : player.getCells()) {
      playerCell.setX(5);
      playerCell.setY(5);
    }
  }
}
