package utils.entityGeneration;

import model.Field;
import org.jetbrains.annotations.NotNull;

/**
 * @author xakep666
 *         <p>
 *         Base class for food generators.
 */
public abstract class FoodGenerator extends EntityGenerator {
    FoodGenerator(@NotNull Field field) {
        super(field);
    }
}
