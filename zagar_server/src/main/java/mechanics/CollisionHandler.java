package mechanics;

import model.Cell;
import model.Food;
import model.PlayerCell;
import model.Virus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import utils.EatComparator;

/**
 * Created by xakep666 on 18.12.16.
 */
public class CollisionHandler {
    @NotNull
    private static final Logger log = LogManager.getLogger(CollisionHandler.class);
    private CollisionHandler() {}

    public static void handleCollision(@NotNull PlayerCell playerCell, @NotNull Cell cell) {
        //can`t collide with itself
        if (cell.equals(playerCell)) return;
        //check actual collision
        double distance = playerCell.getCoordinate().distance(cell.getCoordinate());
        log.debug("Handling collision {} (r{}) with {} (r{}), distance {}",
                playerCell.toString(),
                playerCell.getRadius(),
                cell.toString(),
                cell.getRadius(),
                distance
        );
        if (distance > playerCell.getRadius() + cell.getRadius()) return;
        EatComparator eatComparator = new EatComparator();
        if (cell instanceof Food) {
            log.debug("Player {} ({}, {}) ate food at ({}, {})",
                    playerCell.getOwner().getUser().getName(),
                    playerCell.getCoordinate().getX(),
                    playerCell.getCoordinate().getY(),
                    cell.getCoordinate().getX(),
                    cell.getCoordinate().getY()
            );
            playerCell.eat(cell);
        } else if (cell instanceof Virus) {

        } else if (cell instanceof PlayerCell) {
            int canEat = eatComparator.compare(playerCell,cell);
            if (canEat>0) {
                log.debug("Player {} ate {}`s cell at ({}, {})",
                        playerCell.getOwner().getUser().getName(),
                        ((PlayerCell) cell).getOwner().getUser().getName(),
                        cell.getCoordinate().getX(),
                        cell.getCoordinate().getY()
                );
                playerCell.eat(cell);
            } else if (canEat<0) {
                log.debug("Player {} ate {}`s cell at ({}, {})",
                        ((PlayerCell) cell).getOwner().getUser().getName(),
                        playerCell.getOwner().getUser().getName(),
                        playerCell.getCoordinate().getX(),
                        playerCell.getCoordinate().getY()
                );
                ((PlayerCell) cell).eat(playerCell);
            }
        }
    }
}
