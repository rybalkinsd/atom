package utils.playerPlacing;

import main.ApplicationContext;
import model.Field;
import model.GameConstants;
import model.Player;
import model.PlayerCell;
import org.jetbrains.annotations.NotNull;
import utils.idGeneration.IDGenerator;

import java.awt.geom.Point2D;
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
        int id = ApplicationContext.instance().get(IDGenerator.class).next();
        Random random = new Random();
        PlayerCell newPlayerCell =
                new PlayerCell(player, id, new Point2D.Double(0,0), GameConstants.DEFAULT_PLAYER_CELL_MASS);
        Point2D coordinate = new Point2D.Double(
                newPlayerCell.getRadius() +
                        random.nextInt((int)(field.getSize().getWidth() - 2 * newPlayerCell.getRadius())),
                newPlayerCell.getRadius() +
                        random.nextInt((int)(field.getSize().getHeight() - 2 * newPlayerCell.getRadius()))
        );
        newPlayerCell.setCoordinate(coordinate);
        field.addCell(newPlayerCell);
    }
}
