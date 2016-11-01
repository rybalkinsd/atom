package model;

import main.ApplicationContext;
import org.jetbrains.annotations.NotNull;
import utils.IDGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author apomosov
 */
public class Player {
  private final int id;
  @NotNull
  private String name;
  @NotNull
  private final List<PlayerCell> cells = new ArrayList<>();

  public Player(int id, @NotNull String name) {
    this.id = id;
    this.name = name;
    addCell(new PlayerCell(ApplicationContext.instance().get(IDGenerator.class).next(), 0, 0));
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
}
