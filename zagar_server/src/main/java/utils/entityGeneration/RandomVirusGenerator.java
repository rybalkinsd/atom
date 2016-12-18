package utils.entityGeneration;

import model.Field;
import model.GameConstants;
import model.Virus;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author apomosov
 *
 * Generates and removes viruses randomly with given chance
 */
public class RandomVirusGenerator extends VirusGenerator {
    private final int numberOfViruses;
    private final double removeChance;

    /**
     * @param field field where {@link Virus} cells will be generated/removed
     * @param numberOfViruses total number of {@link Virus} cells on field
     * @param removeChance random number of viruses will be removed with this probability
     */
    public RandomVirusGenerator(@NotNull Field field, int numberOfViruses, double removeChance) {
        super(field);
        this.numberOfViruses = numberOfViruses;
        this.removeChance = removeChance;
        assert (removeChance >= 0 && removeChance <= 1);
    }

    /**
     * Generator logic.
     * Stages:
     * 1) Decide remove or not remove viruses
     * 2) Determine number of removed viruses
     * 3) Remove viruses
     * 4) Determine number of generated viruses to reach numberOfViruses
     * 5) Generate viruses
     * @param elapsed tick time interval
     */
    @Override
    public void generate(@NotNull Duration elapsed) {
        Random random = new Random();
        List<Virus> onField = new ArrayList<>(getField().getCells(Virus.class));
        //Remove or not?
        int virusesToRemove = 0;
        if (random.nextDouble() > 1 - removeChance) {
            virusesToRemove = (int) (onField.size() * random.nextDouble());
            for (int i = 0; i < virusesToRemove; i++) {
                getField().removeCell(onField.get(i));
            }
        }

        int virusRadius = (int) Math.sqrt(GameConstants.VIRUS_MASS / Math.PI);
        int virusesToGenerate = numberOfViruses - onField.size() + virusesToRemove;
        for (int i = 0; i < virusesToGenerate; i++) {
            Virus virus = new Virus(
                    virusRadius + random.nextInt(getField().getWidth() - 2 * virusRadius),
                    virusRadius + random.nextInt(getField().getHeight() - 2 * virusRadius)
            );
            getField().addCell(virus);
        }
    }
}
