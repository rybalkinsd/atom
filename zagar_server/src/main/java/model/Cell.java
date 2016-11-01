package model;

/**
 * @author apomosov
 */
public abstract class Cell {
  private int x;
  private int y;
  private int radius;
  private int mass;

  public Cell(int x, int y, int mass) {
    this.x = x;
    this.y = y;
    this.mass = mass;
    updateRadius();
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

  private void updateRadius(){
    this.radius = (int) Math.sqrt(this.mass/Math.PI);
  }
}
