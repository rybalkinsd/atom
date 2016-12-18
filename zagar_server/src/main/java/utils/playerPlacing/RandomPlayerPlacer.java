package utils.playerPlacing;

import main.ApplicationContext;
import model.Field;
import model.Player;
import model.PlayerCell;
import org.jetbrains.annotations.NotNull;
import utils.idGeneration.IDGenerator;

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
        PlayerCell newPlayerCell = new PlayerCell(player, id, 0, 0);
        newPlayerCell.setX(newPlayerCell.getRadius() + random.nextInt(field.getWidth() - 2 * newPlayerCell.getRadius()));
        newPlayerCell.setY(newPlayerCell.getRadius() + random.nextInt(field.getHeight() - 2 * newPlayerCell.getRadius()));
        field.addCell(newPlayerCell);
    }
}
