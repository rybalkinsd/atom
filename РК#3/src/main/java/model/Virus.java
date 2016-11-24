package model;

/**
 * @author apomosov
 */
public class Virus extends Cell {
  public Virus(int x, int y) {
    super(x, y, GameConstants.VIRUS_MASS);
  }
}
