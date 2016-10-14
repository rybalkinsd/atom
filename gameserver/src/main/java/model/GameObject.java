package model;

import java.awt.*;
import java.util.Random;

/**
 * Created by Artem on 10/11/16.
 *
 * Abstract class for all game objects
 */
public abstract class GameObject {

    protected double x;
    protected double y;
    protected double radius;
    protected Color color; //some string representing color of the object

    public GameObject() {
        Random random = new Random();
        this.x = random.nextDouble() * GameConstants.SIZE_OF_FIELD;
        this.y = random.nextDouble() * GameConstants.SIZE_OF_FIELD;
        this.radius = 0;
        this.color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
    }

    public GameObject(double radius) {
        Random random = new Random();
        this.x = random.nextDouble() * GameConstants.SIZE_OF_FIELD;
        this.y = random.nextDouble() * GameConstants.SIZE_OF_FIELD;
        this.radius = radius;
        this.color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
    }

    public GameObject(double x, double y, double radius) {
        Random random = new Random();
        this.x = x * GameConstants.SIZE_OF_FIELD;
        this.y = y * GameConstants.SIZE_OF_FIELD;
        this.radius = radius;
        this.color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
    }

    public GameObject(double radius, Color color) {
        Random random = new Random();
        this.x = random.nextDouble() * GameConstants.SIZE_OF_FIELD;
        this.y = random.nextDouble() * GameConstants.SIZE_OF_FIELD;
        this.radius = radius;
        this.color = color;
    }

    public GameObject(double x, double y, double radius, Color color) {
        this.x = x * GameConstants.SIZE_OF_FIELD;
        this.y = y * GameConstants.SIZE_OF_FIELD;
        this.radius = radius;
        this.color = color;
    }
}
