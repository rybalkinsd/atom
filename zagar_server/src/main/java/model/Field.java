package model;

import matchmaker.MatchMakerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author apomosov
 */
public class Field {
  @NotNull
  private final Logger log = LogManager.getLogger(MatchMakerImpl.class);

  private final int width;
  private final int height;
  @NotNull
  private final List<Virus> viruses = new ArrayList<>();
  @NotNull
  private final ConcurrentHashSet<Food> foods = new ConcurrentHashSet<>();

  public Field() {
    this.width = GameConstants.FIELD_WIDTH;
    this.height = GameConstants.FIELD_HEIGHT;
  }

  @NotNull
  public List<Virus> getViruses() {
    return viruses;
  }

  @NotNull
  public ConcurrentHashSet<Food> getFoods() {
    return foods;
  }

  public void addFood(Food foodInstance){
    //log.info(foods);
    foods.add(foodInstance);
    log.info("Food instance " + foodInstance.toString() + " added to " + foods);
  }
  
  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
