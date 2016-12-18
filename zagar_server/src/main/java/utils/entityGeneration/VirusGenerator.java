package utils.entityGeneration;

import model.Field;
import org.jetbrains.annotations.NotNull;

/**
 * @author xakep666
 *
 * Base class for virus generators
 */
public abstract class VirusGenerator extends EntityGenerator {
    VirusGenerator(@NotNull Field field) {
        super(field);
    }
}
