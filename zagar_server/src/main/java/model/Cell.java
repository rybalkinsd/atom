package model;

import utils.SequentialIDGenerator;

/**
 * @author apomosov
 */
public abstract class Cell {
    public static final SequentialIDGenerator idGenerator = new SequentialIDGenerator();

    private Location location;
    private Location newLocation = new Location(0,0);
    private int radius;
    private int mass;
    private double speed;

    public Cell(Location location, int mass) {
        this.location = location;
        this.mass = mass;
        updateRadius();
        updateSpeed();
    }

    public Location getLocation(){return location;}

    public void setLocation(Location location){this.location = location;}

    public void setNewLocation(Location newLocation){
        this.newLocation = newLocation;
    }

    public Location getNewLocation(){
        return newLocation;
    }

    public int getRadius() {
        return radius;
    }

    public int getMass() {
        return mass;
    }

    public double getSpeed(){
        return speed;
    }

    public void setMass(int mass) {
        this.mass = mass;
        updateRadius();
        updateSpeed();
    }

    private void updateRadius(){
        this.radius = (int) Math.sqrt(this.mass/Math.PI);
    }

    private void updateSpeed(){ speed = 333 * Math.pow(mass, -1/3); }
}
