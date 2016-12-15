package utils;

import model.Field;
import model.Player;
import model.PlayerCell;
import org.jetbrains.annotations.NotNull;
import java.util.Random;

public class RandomPlayerPlacer implements PlayerPlacer {
  private Field field;

  public RandomPlayerPlacer() {}

  @Override
  public void setField(Field field){
    this.field = field;
  }

  @Override
  public void place(@NotNull Player player) {
    assert(player.getCells().size() == 1);
    Random random = new Random();
    for (PlayerCell playerCell : player.getCells()) {
      playerCell.setX(playerCell.getRadius() +
              random.nextInt(field.getWidth() - 2 * playerCell.getRadius()));
      playerCell.setY(playerCell.getRadius() +
              random.nextInt(field.getHeight() - 2 * playerCell.getRadius()));
    }
  }
}