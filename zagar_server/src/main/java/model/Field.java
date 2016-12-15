package model;

import org.eclipse.jetty.util.ConcurrentHashSet;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class Field {
  private final int width;
  private final int height;
  @NotNull
  private final ArrayList<Virus> viruses = new ArrayList<>();
  @NotNull
  private final ConcurrentHashSet<Food> foods = new ConcurrentHashSet<>();
  public Field() {
    this.width = GameConstants.FIELD_WIDTH;
    this.height = GameConstants.FIELD_HEIGHT;
  }

  @NotNull
  public ArrayList<Virus> getViruses() {
    return viruses;
  }

  @NotNull
  public ConcurrentHashSet<Food> getFoods() {
    return foods;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}