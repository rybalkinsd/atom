package zagar.util;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Created by eugene on 12/3/16.
 */
public enum  Colors {
    RED(0xF4,0x43,0x36),
    DEEP_PURPLE(0x67,0x3A,0xB7),
    CYAN(0x03,0xA9,0xF4);

    private int r;
    private int g;
    private int b;

    Colors(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    @NotNull
    private static Random generator = new Random();
    public static Colors getRandom(){
        return Colors.values()[generator.nextInt(values().length)];
    }
}
