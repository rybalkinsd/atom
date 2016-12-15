package utils;

import java.awt.Color;
import java.util.Random;

public class ColorUtils {

    public static Color generateRandomColor() {
        Random random = new Random();
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        return new Color(r, g, b);
    }

}
