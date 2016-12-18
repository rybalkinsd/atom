package utils;

import model.GameConstants;
import model.Location;
import model.GameField;
import model.Virus;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * @author apomosov
 */
public class RandomVirusGenerator implements VirusGenerator {
    @NotNull
    private final GameField field;
    private final int numberOfViruses;


    public RandomVirusGenerator(@NotNull GameField field, int numberOfViruses) {
        this.field = field;
        this.numberOfViruses = numberOfViruses;
    }

    @Override
    public void tick(long elapsedNanos) {

    }

    @Override
    public Virus generate() {
        Random random = new Random();
        int virusRadius = (int) Math.sqrt(GameConstants.VIRUS_MASS / Math.PI);
        Virus virus = new Virus( new Location(virusRadius + random.nextInt(field.getWidth() - 2 * virusRadius),
              virusRadius + random.nextInt(field.getHeight() - 2 * virusRadius)));
        return virus;
    }
}
