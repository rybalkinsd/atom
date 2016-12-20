package utils.playerPlacing;

import main.ApplicationContext;
import model.Field;
import model.GameConstants;
import model.Player;
import model.PlayerCell;
import org.jetbrains.annotations.NotNull;
import utils.idGeneration.IDGenerator;

import java.awt.geom.Point2D;

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
    PlayerCell playerCell
            = new PlayerCell(player, id, new Point2D.Double(5,5), GameConstants.DEFAULT_PLAYER_CELL_MASS);
    field.addCell(playerCell);
  }
}
