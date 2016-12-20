package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import utils.EatComparator;
import utils.IDGenerator;
import utils.SequentialIDGenerator;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author apomosov
 */
public class Player {
  public static final IDGenerator idGenerator = new SequentialIDGenerator();
  private final int id;
  @NotNull
  private final static Logger log = LogManager.getLogger(Player.class);
  @NotNull
  private String name;
  @NotNull
  private  List<PlayerCell> cells = new CopyOnWriteArrayList<>();
  @NotNull
  private
  EatComparator eatComparator = new EatComparator();
  @NotNull
  private CopyOnWriteArraySet<SplitFood> splitFoodSet = new CopyOnWriteArraySet<>();

  private double lastUpdate;
  private double lastSplit;
  private boolean joining;
  private double lastEject;
  private boolean respawn = false;
  private double timeToJoin;


  public Player(int id, @NotNull String name) {
    this.id = id;
    this.name = name;
    lastSplit = System.currentTimeMillis();
    lastEject = System.currentTimeMillis();
    joining = false;
    lastUpdate = System.currentTimeMillis();
    addCell(new PlayerCell(Cell.idGenerator.next(), 0, 0));
  }

  public void cloneLists(){
    cells = new CopyOnWriteArrayList<>(cells);
    splitFoodSet = new CopyOnWriteArraySet<>(splitFoodSet);
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

  public boolean isRespawn(){
    return respawn;
  }

  public void setRespawn(boolean b){
    this.respawn = b;
  }

  public void move(double x, double y){
    checkSplit();
    DoubleVector centre = null;

    double dTime =  System.currentTimeMillis() - lastUpdate;

    for (PlayerCell playerCell:cells) {
      playerCell.updateVelocity(x, y);

      if (!playerCell.getUngovernable() && !joining)
        for (PlayerCell playerCell1 : cells) {
          if (!playerCell.equals(playerCell1) && playerCell.pushOff(playerCell1)) {
            DoubleVector doubleVector = new DoubleVector(
                    playerCell.getX() - playerCell1.getX(),
                    playerCell.getY() - playerCell1.getY()
            );

            playerCell.getVelocity().add(doubleVector.normalize().multi(playerCell.getVelocity().length() * 1.5));
          }
        }



      if (new Double(playerCell.getVelocity().getX()).equals(Double.NaN) ||
              new Double(playerCell.getVelocity().getY()).equals( Double.NaN))
        playerCell.setVelocity(new DoubleVector(0, 0));



      int newX = (int)(playerCell.getX() + dTime*playerCell.getVelocity().getX());
      int newY = (int)(playerCell.getY() + dTime*playerCell.getVelocity().getY());

      if(joining){
        centre = findCentre();
        newX += (int) 0.9 * ((centre.getX() - playerCell.getX()) / timeToJoin * dTime);
        newY += (int) 0.9 * ((centre.getY() - playerCell.getY()) / timeToJoin * dTime);
        timeToJoin-=dTime;
      }

      if (newX <= 0){
        if (playerCell.getUngovernable())
            playerCell.setVelocity(DoubleVector.zero());
        newX = 0;
        }
        if (newX > GameConstants.FIELD_WIDTH) {
          if (playerCell.getUngovernable())
            playerCell.setVelocity(DoubleVector.zero());
          newX = GameConstants.FIELD_WIDTH;
        }

        if (newY <= 0){
          if (playerCell.getUngovernable())
            playerCell.setVelocity(DoubleVector.zero());

          newY = 0;
        }

        if (newY > GameConstants.FIELD_HEIGHT) {
          if (playerCell.getUngovernable())
            playerCell.setVelocity(DoubleVector.zero());

          newY = GameConstants.FIELD_HEIGHT;
        }

        playerCell.setX(newX);
        playerCell.setY(newY);
      }

    lastUpdate = System.currentTimeMillis();
  }

  public boolean eat(Cell food){
    if (cells.contains(food)){
      return false;
    }
    for(PlayerCell playerCell: cells) {
      double dx = playerCell.getX() - food.getX();
      double dy = playerCell.getY() - food.getY();

      if ((Math.sqrt(dx*dx+dy*dy) < (playerCell.getMass() - food.getMass()))&&
              eatComparator.compare(playerCell,food)==1
              ) {
        if(food.getClass() != Virus.class){
          playerCell.setMass(playerCell.getMass()+food.getMass());
          if (food.getClass() == Food.class)
            log.info("Player {} just now is eat the food in thread {}",this.getName(),Thread.currentThread());
          else
            log.info("Player {} just now is eat the other player in thread {}",this.getName(), Thread.currentThread());
          return true;
        }else {
          virusSplit(playerCell);
          log.info("Player {} just now is eat the virus in thread {}",this.getName(), Thread.currentThread());
          return true;
        }
      }
    }
   return false;
  }

  private void virusSplit(PlayerCell playerCell){
    joining = false;
    timeToJoin=-1;
    lastSplit = System.currentTimeMillis();
    int newMass = playerCell.getMass()/8;


      for  (int i =0;i <8;i++) {
      float x = 0;
      float y = 0;
      PlayerCell newPlayerCell = new PlayerCell(Cell.idGenerator.next(),playerCell.getX(),playerCell.getY());

      switch (i) {
        case 0:
          x = newPlayerCell.getX()+5000;
          y = newPlayerCell.getY()+5000;
          break;
        case 1:
          x = newPlayerCell.getX()+5000;
          y = newPlayerCell.getY()-5000;
          break;
        case 2:
          x = newPlayerCell.getX()-5000;
          y = newPlayerCell.getY()+ 5000;
          break;
        case 3:
          x = newPlayerCell.getX()-5000;
          y = newPlayerCell.getY()-5000;
          break;
        case 4:
          x = newPlayerCell.getX()+2500;
          y = newPlayerCell.getY();
          break;
        case 5:
          x = newPlayerCell.getX();
          y = newPlayerCell.getY()-2500;
          break;
        case 6:
          x = newPlayerCell.getX()-2500;
          y = newPlayerCell.getY();
          break;
        case 7:
          x = newPlayerCell.getX();
          y = newPlayerCell.getY()+2500;
          break;
      }

      newPlayerCell.setMass(newMass);
      newPlayerCell.updateVelocity(x,y);


      newPlayerCell.getVelocity().multi(2);

      newPlayerCell.setUngovernable(true);
      addCell(newPlayerCell);
    }

    cells.remove(playerCell);
  }

  public void split(double x, double y){
    for (PlayerCell playerCell:cells) {
        if(playerCell.getMass() >= GameConstants.MIN_MASS_FOR_EJECT){
          joining = false;
          timeToJoin = -1;
          lastSplit = System.currentTimeMillis();
          playerCell.setMass(playerCell.getMass()/2);

          PlayerCell newPlayerCell = new PlayerCell(Cell.idGenerator.next(),playerCell.getX(),playerCell.getY());

          newPlayerCell.setMass(playerCell.getMass());
          newPlayerCell.updateVelocity(x,y);


          newPlayerCell.getVelocity().multi(4);

          newPlayerCell.setUngovernable(true);
          addCell(newPlayerCell);
        }
    }
  }

  private void checkSplit(){
    if( lastSplit != -1 && System.currentTimeMillis() - lastSplit >GameConstants.MAX_DISCONNECTING_TIME ){
      PlayerCell mainCell = cells.get(0);
      for(int i = 1;i<cells.size();i++){
        mainCell.setMass(mainCell.getMass() + cells.get(i).getMass());
      }
      cells.clear();
      cells.add(mainCell);
      lastSplit = -1;
      timeToJoin =-1;
      joining = false;
      log.info("Player {} just now is merged",this.getId());
    }

    if( lastSplit != -1 &&
            System.currentTimeMillis() - lastSplit > GameConstants.MAX_DISCONNECTING_TIME - GameConstants.JOINING_TIME)
      joining = true;

    if(joining && timeToJoin ==-1){
      timeToJoin = GameConstants.JOINING_TIME;
    }
  }

  private DoubleVector findCentre(){
    DoubleVector doubleVector = new DoubleVector();
      for (PlayerCell playerCell:cells){
        doubleVector.add(new DoubleVector(playerCell.getX(),playerCell.getY()));
    }
    doubleVector.multi((double)1/(double)cells.size());
    return  doubleVector;
  }

  public void eject(double x, double y){
    if(System.currentTimeMillis() - lastEject < GameConstants.RELOAD_EJECT_TIME)
      return;

    for (PlayerCell playerCell:cells) {
      if (playerCell.getMass() < GameConstants.MIN_MASS_TO_SPLIT)
        continue;

      playerCell.setMass(playerCell.getMass() - GameConstants.FOOD_MASS);
      DoubleVector dv = new DoubleVector(x - playerCell.getX(),y - playerCell.getY());
      dv.normalize().multi(playerCell.getMass());
      SplitFood splitFood = new SplitFood((int)(playerCell.getX() + dv.getX())  , (int)(playerCell.getY() + dv.getY() ));

      if(splitFood.getX() < 0)
        splitFood.setX(0);
      if(splitFood.getY() < 0)
        splitFood.setY(0);

      if(splitFood.getX() > GameConstants.FIELD_WIDTH)
        splitFood.setX(GameConstants.FIELD_WIDTH);

      if (splitFood.getY() > GameConstants.FIELD_HEIGHT)
        splitFood.setY(GameConstants.FIELD_HEIGHT);

      dv.normalize();
      splitFood.setVelocity(dv);
      splitFoodSet.add(splitFood);
      lastEject = System.currentTimeMillis();
    }
  }

  @NotNull
  public CopyOnWriteArraySet<SplitFood> getSplitFoodSet() {
    return splitFoodSet;
  }

  @NotNull
  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' +
        '}';
  }

  public void startRespawn() {
    lastSplit = System.currentTimeMillis();
    lastEject = System.currentTimeMillis();
    joining = false;
    lastUpdate = System.currentTimeMillis();
    addCell(new PlayerCell(Cell.idGenerator.next(), 0, 0));
  }
}
