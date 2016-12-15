package model;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author apomosov
 */
public class Field {
  private final int width;
  private final int height;
  @NotNull
  private final List<Virus> viruses = new CopyOnWriteArrayList<>();
  //@NotNull
  //private final HashSet<Food> foodSet = new HashSet<>();

  @NotNull
  private final CopyOnWriteArraySet<Food> foodSet = new CopyOnWriteArraySet<>();
  @NotNull
  private final CopyOnWriteArraySet<SplitFood> splitFoodSet = new CopyOnWriteArraySet<>();


  public Field() {
    this.width = GameConstants.FIELD_WIDTH;
    this.height = GameConstants.FIELD_HEIGHT;
  }


  @NotNull
  public List<Virus> getViruses() {
    return viruses;
  }

  @NotNull
  public CopyOnWriteArraySet<Food> getFoodSet() {
    return foodSet;
  }

  @NotNull
  public CopyOnWriteArraySet<SplitFood> getSplitFoodSet() {
    return splitFoodSet;
  }

  public void addFood(Food food){
    foodSet.add(food);
  }
  public void addSplitFood(SplitFood food){
    splitFoodSet.add(food);
  }
  public void addVirus(Virus virus) {viruses.add(virus);}

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
