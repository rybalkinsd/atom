package model;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.UUID;
import java.text.DecimalFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jetbrains.annotations.NotNull;
/**
 * Created by helen on 10.10.16.
 * @author helen
 * Abstract class Item
 */
public abstract class Item implements GameConstants {
    @NotNull
    protected static final Logger log = LogManager.getLogger(Item.class);

    //https://www.mkyong.com/java/java-display-double-in-2-decimal-points
    //makes user readable format
    protected static DecimalFormat df2 = new DecimalFormat(".##");


    @NotNull
    private String id;

    private Point2D position;
    private  Color itemColor;
    @NotNull
    protected int mass;

    /**
     * Default constructor
     * Generates random coordinates, color and id
     */
    public Item(){
        this.setRandomId();
        this.setRandomColor();
        this.setRandomPosition();
    }

    /**
     * Constructor with parameter
     * @param color color of item
     */
    public Item(Color color){
        this.setColor(color);
        this.setRandomPosition();
        this.setRandomId();
    }

    /**
     * Generate random coordinates according to grid size
     */
    private void setRandomPosition(){
        Random rand = new Random();
        this.position = new Point2D.Double(rand.nextDouble()*GRID_SIZE,rand.nextDouble()*GRID_SIZE);
    }

    /**
     * Generate color using random r,g,b values
     */
    private void setRandomColor(){
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color randomColor = new Color(r, g, b);
        this.itemColor=randomColor;
    }

    private void setColor(Color color){
        this.itemColor=color;
    }

    /**
     * Ugly fix for generating id
     */
    private void setRandomId(){
        String uniqueID = UUID.randomUUID().toString();
        this.id=uniqueID;
    }

    /**
     * Abstract method
     * @param mass item new mass
     */
    public abstract void setMass(int mass);

    //getters
    public String getId(){
        return this.id;
    }
    public Point2D getPosition(){
        return this.position;
    }
    public Color getColor(){
        return this.itemColor;
    }
    public int getMass() {return this.mass;}
}

/**
 * Cell - instance of player in the grid
 * This is a circle with random color with label on it
 *
 */
class Cell extends Item {
    //private int direction; //TODO: to implement direction and speed
    //private int speed;
    @NotNull
    private String Name;

    /**
     * Create new Cell for player
     * @param name owner of new cell
     */
    public Cell(String name){
        super();
        this.setMass(CELL_START_MASS);
        this.setName(name);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    /**
     * Sets owner
     * @param name player-owner
     */
    private void setName(String name){
        this.Name=name;
    }
    public String getName(){
        return this.Name;
    }

    @Override
    public void setMass(int mass) {
        this.mass=mass;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "/color=" + this.getColor() +
                "/position=(" + df2.format(this.getPosition().getX()) + ";" + df2.format(this.getPosition().getY()) +
                ")/mass=" + this.getMass() +
                '}';
    }
//TODO: it can move, split, eject, grow - implement these functions
}

/**
 * Virus - green circle
 * hides small cells, attacks big cells
 */
class Virus extends Item {
    /**
     * Creates new Virus
     */
    public Virus(){
        super(Color.green);
        this.setMass(calcRandMass());
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    /**
     * Calculates random mass for virus
     * from VIRUS_MAX_MASS to VIRUS_MIN_MASS
     * @return (int) new random mass
     */
    private int calcRandMass() {
        Random rand = new Random();
        return rand.nextInt(VIRUS_MAX_MASS - VIRUS_MIN_MASS + 1) + VIRUS_MIN_MASS;
    }

    @Override
    public void setMass(int mass) {
        this.mass=mass;
    }

    @Override
    public String toString() {
        return "Virus{" +
                "/color=" + this.getColor()+
                "/position=(" + df2.format(this.getPosition().getX()) + ";" + df2.format(this.getPosition().getY()) +
                ")/mass=" + this.getMass() +
                '}';
    }
}

/**
 * Food for cells
 * Static tiny items with mass=1
 */
class Pellet extends Item {
    /**
     * Creates new Pellet
     */
    public Pellet(){
        super();
        this.setMass(PELLET_MASS);
        if (log.isInfoEnabled()) {
            log.info(toString() + " created");
        }
    }

    @Override
    public void setMass(int mass) {
        this.mass=mass;
    }

    @Override
    public String toString() {
        return "Pellet{" +
                "/color=" + this.getColor() +
                "/position=(" + df2.format(this.getPosition().getX()) + ";" + df2.format(this.getPosition().getY()) +
                ")/mass=" + this.getMass() +
                '}';
    }
}
