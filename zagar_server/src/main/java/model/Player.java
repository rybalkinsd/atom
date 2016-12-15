package model;

import main.ApplicationContext;
import network.ClientConnectionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import utils.IDGenerator;
import utils.SequentialIDGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author apomosov
 */
public class Player {
  private final static @NotNull Logger log = LogManager.getLogger(ClientConnectionHandler.class);

  public static final IDGenerator idGenerator = new SequentialIDGenerator();
  private final int id;
  @NotNull
  private String name;
  @NotNull
  private final List<PlayerCell> cells = new ArrayList<>();
  private double velocity;
  private double direction = Math.PI;
  private double center_x;
  private double center_y;
  private boolean isRespawnable = false;

  public Player(int id, @NotNull String name) {
    this.id = id;
    this.name = name;
    addCell(new PlayerCell(Cell.idGenerator.next(), 0, 0));
  }

  public void markRespawn(){
    isRespawnable = true;
  }

  public boolean getIsRespawnable(){
    return isRespawnable;
  }

  public void respawned(){
    isRespawnable = false;
  }

  private void updateCenter (){
    center_x = 0;
    center_y = 0;
    for (Cell cell: cells){
      center_y += cell.getY()*cell.getMass();
      center_x += cell.getX()*cell.getMass();
    }
    center_x = center_x/getScore();
    center_y = center_y/getScore();
  }

  public void addCell(@NotNull PlayerCell cell) {
    cells.add(cell);
  }

  public void removeCell(@NotNull PlayerCell cell) {
    cells.remove(cell);
    if (cells.size() == 0){
      //Auto respawn
    }
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

  public Integer getScore(){
    Integer score = new Integer(0);
    for (PlayerCell e: getCells()){
      score += e.getMass();
    }
    return score;
  }

  public void move(float dx, float dy){
    if ((dx == 0) && (dy == 0)){
      log.info(this + " was not moved on " + dx + " " + dy);
    }
    else {
      direction = Math.atan2(-dy, dx);
      velocity = 2 + (int) Math.round(10 / Math.sqrt(Math.sqrt(getScore())));
      for (PlayerCell cell : cells) {
        cell.move(direction, velocity);
      }
      log.info(this + " was moved on " + dx + " " + dy);
    }
  }

  public Food ejectMass(){
    PlayerCell mainCell = getMainCell();
    if (mainCell.getMass() >= 30){
      mainCell.setMass(mainCell.getMass() - 10);
      int x = mainCell.getX();
      int y = mainCell.getY();
      int r = mainCell.getRadius();

      Food newFoodCell = new Food((int) Math.round(x - r * Math.cos(direction)),(int) Math.round( y + r * Math.sin(direction)));
      mainCell.move(direction, r/2);

      log.info(this + " ejected mass");
      return newFoodCell;
    }
    return null;
  }

  private PlayerCell getMainCell(){
    PlayerCell mainCell = cells.get(0);
    for (PlayerCell cell: cells){
      if (cell.getMass() > mainCell.getMass()){
        mainCell = cell;
      }
    }
    return mainCell;
  }

  public void split(){
    PlayerCell mainCell = getMainCell();
    int mass = mainCell.getMass();
    if (mass >= 40){
      int radius = mainCell.getRadius();

      //way to split coordinats including direction
      int x = mainCell.getX();
      int y = mainCell.getY();
      int dx =(int) (Math.cos(direction + Math.PI)*radius /2);
      int dy =(int) (Math.sin(direction + Math.PI)*radius /2);

      PlayerCell newCell = new PlayerCell(Cell.idGenerator.next(), x + dx, y - dy);
//      mainCell.setY(y - dy);
//      mainCell.setX(x - dx);
      mainCell.move(direction - Math.PI, radius / 2);

      newCell.setMass(mass/2);
      mass -= mass/2;
      mainCell.setMass(mass);

      cells.add(newCell);
      log.info(this + " was splited");
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' +
        '}';
  }
}
