package model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author apomosov
 */
public class Field {
  private final int width;
  private final int height;
  @NotNull
  private final List<Virus> viruses = new ArrayList<>();
  @NotNull
  private final HashSet<Food> foods = new HashSet<>();

  public Field() {
    this.width = GameConstants.FIELD_WIDTH;
    this.height = GameConstants.FIELD_HEIGHT;
  }

  @NotNull
  public List<Virus> getViruses() {
    return viruses;
  }

  @NotNull
  public HashSet<Food> getFoods() {
    return foods;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
