package utils;

import model.Field;
import model.Player;
import model.PlayerCell;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * @author apomosov
 */
public class RandomPlayerPlacer implements PlayerPlacer {
  @NotNull
  private final Field field;

  public RandomPlayerPlacer(@NotNull Field field) {
    this.field = field;
  }

  @Override
  public void place(@NotNull Player player) {
    try {
      assert(player.getCells().size() == 1);
    } catch (Exception e){
      player.getCells().clear();
      player.addCell(new PlayerCell(PlayerCell.idGenerator.next(), 0,0));
    }
    Random random = new Random();
    for (PlayerCell playerCell : player.getCells()) {
      playerCell.setX(playerCell.getRadius() + random.nextInt(field.getWidth() - 2 * playerCell.getRadius()));
      playerCell.setY(playerCell.getRadius() + random.nextInt(field.getHeight() - 2 * playerCell.getRadius()));
    }
    player.respawned();
  }
}
