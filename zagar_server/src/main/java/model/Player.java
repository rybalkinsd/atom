package model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import utils.IDGenerator;
import utils.SequentialIDGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author apomosov
 */
public class Player {
  public static final IDGenerator idGenerator = new SequentialIDGenerator();
  private final int id;
  @NotNull
  private String name;
  @NotNull
  private final List<PlayerCell> cells = new ArrayList<>();

  public Player(int id, @NotNull String name) {
    this.id = id;
    this.name = name;
    addCell(new PlayerCell(id, 0, 0));
  }

  public void addCell(@NotNull PlayerCell cell) {
    cells.add(cell);
  }

  public void removeCell(@NotNull PlayerCell cell) {
    cells.remove(cell);
  }

  @NotNull
  public String getName() {
    return name;
  }

  public void setName(@NotNull String name) {
    this.name = name;
  }

  @NotNull
  public List<PlayerCell> getCells() {
    return cells;
  }

  public int getId() {
    return id;
  }

  @NotNull
  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' +
        '}';
  }

  private int score = getAllMass();

  private int getAllMass(){
      return cells.stream().map(Cell::getMass).reduce(Math::addExact).orElse(0);
  }

  public void updateScore(){
      int mass = getAllMass();
      if (mass > score) {
          score = mass;
      }
  }

  public int getScore() {
    return score;
  }

  @TestOnly
  public void setScore(int score) {
      this.score = score;
  }
}
