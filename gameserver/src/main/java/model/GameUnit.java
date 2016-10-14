package model;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.*;

public class GameUnit {
    @NotNull
    private Location location;
    @NotNull
    private Color color;
    private double speed = 0;
    private double radius;
    private int mass;

    public GameUnit(@NotNull Color color, @NotNull Location location, double speed, int mass) {
        this.speed = speed;
        this.color = color;
        this.location = location;
        this.mass = mass;
        this.radius = calcRadius(mass);
    }

    public GameUnit(@NotNull Color color, @NotNull Location location, int mass) {
        this.color = color;
        this.location = location;
        this.mass = mass;
        this.radius = calcRadius(mass);
    }

    public GameUnit(@NotNull Location location, double speed, int mass) {
        this.speed = speed;
        this.location = location;
        this.color = getRandomColor();
        this.mass = mass;
        this.radius = calcRadius(mass);
    }

    public GameUnit(@NotNull Location location, int mass) {
        this.color = getRandomColor();
        this.location = location;
        this.mass = mass;
        this.radius = calcRadius(mass);
    }

    @NotNull
    public Location getLocation() {
        return this.location;
    }

    public void setLocation(@NotNull Location newLocation) {
        this.location = newLocation;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpped(int speed) {
        this.speed = speed;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getMass() {
        return this.mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    @NotNull
    public Color getColor() {
        return this.color;
    }

    static Color getRandomColor() {
        Random random = new Random();
        switch (random.nextInt(5)) {
            case 0: return Color.BLACK;
            case 1: return Color.BLUE;
            case 2: return Color.GREEN;
            case 3: return Color.ORANGE;
            case 4: return Color.RED;
            case 5: return Color.YELLOW;
            default: return Color.PINK;
        }
    }

    private double calcRadius(int mass) {
        return sqrt(mass/PI);
    }
}
