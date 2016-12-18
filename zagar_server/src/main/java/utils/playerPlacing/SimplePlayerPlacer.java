package utils.playerPlacing;

import main.ApplicationContext;
import model.Field;
import model.Player;
import model.PlayerCell;
import org.jetbrains.annotations.NotNull;
import utils.idGeneration.IDGenerator;

/**
 * @author apomosov
 */
public class SimplePlayerPlacer implements PlayerPlacer {
  @NotNull
  private Field field;

  public SimplePlayerPlacer(@NotNull Field field) {
    this.field = field;
  }

  @Override
  public void place(@NotNull Player player) {
    int id = ApplicationContext.instance().get(IDGenerator.class).next();
    PlayerCell playerCell = new PlayerCell(player, id, 0, 0);
    playerCell.setX(5);
    playerCell.setY(5);
    field.addCell(playerCell);
  }
}
