package model;

import utils.IDGenerator;
import utils.SequentialIDGenerator;

/**
 * @author apomosov
 */
public abstract class Cell {
  public static final IDGenerator idGenerator = new SequentialIDGenerator();

  private final int cellId;

  private int x;
  private int y;
  private int radius;
  private int mass;

  private float speedX;
  private float speedY;

  public Cell(int x, int y, int mass) {
    this.x = x;
    this.y = y;
    this.cellId = idGenerator.next();
    setMass(mass);
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public float getSpeedX() {
    return speedX;
  }

  public void setSpeedX(float speedX) {
    this.speedX = speedX;
  }

  public float getSpeedY() {
    return speedY;
  }

  public void setSpeedY(float speedY) {
    this.speedY = speedY;
  }

  public int getRadius() {
    return radius;
  }

  public int getMass() {
    return mass;
  }

  public void setMass(int mass) {
    this.mass = mass;
    updateRadius();
  }

  public int getCellId() {
    return cellId;
  }

  public int distance(Cell to){
    return (int) Math.sqrt(
      Math.pow(getX() - to.getX(), 2.0) +
              Math.pow(getY() - to.getY(), 2.0)
    );
  }

  private void updateRadius(){
    this.radius = (int) Math.sqrt(this.mass/Math.PI);
  }
}
