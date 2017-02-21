package utils;

import model.Location;
import model.GameField;
import model.Virus;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Created by Max on 15.12.2016.
 */
public class UniformVirusGenerator implements VirusGenerator{
    @NotNull
    private final GameField field;
    private final int threshold;
    private final double foodPerSecond=0.1;
    Random random = new Random();

    @Override
    public void tick(long elapsedNanos) {

    }
    public UniformVirusGenerator(@NotNull GameField field, int threshold) {
        this.field = field;
        this.threshold = threshold;
    }

    @Override
    public Virus generate(){
        return new Virus(new Location(10+random.nextInt(field.getWidth()-20),10+random.nextInt(field.getHeight()-20)));
    }
}
