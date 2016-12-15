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
  private final List<PlayerCell> cells = new CopyOnWriteArrayList<>();
  @NotNull
  private
  EatComparator eatComparator = new EatComparator();
  @NotNull
  private final CopyOnWriteArraySet<SplitFood> splitFoodSet = new CopyOnWriteArraySet<>();

  private double lastUpdate;
  private double lastSplit;
  private boolean joining;
  private double lastEject;


  public Player(int id, @NotNull String name) {
    this.id = id;
    this.name = name;
    lastSplit = System.currentTimeMillis();
    lastEject = System.currentTimeMillis();
    joining = false;
    lastUpdate = System.currentTimeMillis();
    addCell(new PlayerCell(Cell.idGenerator.next(), 0, 0));
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

  public void move(double x, double y){
    checkSplit();
    DoubleVector centre = null;
    if(joining)
      centre = findCentre();
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

        if(joining){
          DoubleVector dCentre = new DoubleVector(centre);
          dCentre.sub(new DoubleVector(playerCell.getX(),playerCell.getY()));
          dCentre.multi(1 /GameConstants.JOINING_TIME);
          playerCell.getVelocity().add(dCentre);
        }



      if (new Double(playerCell.getVelocity().getX()).equals(Double.NaN) ||
              new Double(playerCell.getVelocity().getY()).equals( Double.NaN))
        playerCell.setVelocity(new DoubleVector(0, 0));



      int newX = (int)(playerCell.getX() + dTime*playerCell.getVelocity().getX());
      int newY = (int)(playerCell.getY() + dTime*playerCell.getVelocity().getY());


      if (newX <= 0){
          newX = 0;
        }
        if (newX > GameConstants.FIELD_WIDTH)
          newX = GameConstants.FIELD_WIDTH;

        if (newY <= 0){
          newY = 0;
        }
        if (newY > GameConstants.FIELD_HEIGHT)
          newY = GameConstants.FIELD_HEIGHT;

        playerCell.setX(newX);
        playerCell.setY(newY);
      }

    lastUpdate = System.currentTimeMillis();
  }

  public boolean eat(Cell food){
    for(PlayerCell playerCell: cells) {
      double dx = playerCell.getX() - food.getX();
      double dy = playerCell.getY() - food.getY();

      if ((Math.sqrt(dx*dx+dy*dy) < (playerCell.getMass() + food.getMass()))&&
              eatComparator.compare(playerCell,food)==1
              ) {
        if(food.getClass() != Virus.class){
          playerCell.setMass(playerCell.getMass()+food.getMass());
          if (food.getClass() == Food.class)
            log.info("Player {} just now is eat the food",this.getId());
          else
            log.info("Player {} just now is eat the other player",this.getId());
          return true;
        }else {
          split(playerCell.getX()+1, playerCell.getY()+1,2);
          log.info("Player {} just now is eat the virus",this.getId());
          return true;
        }
      }
    }
   return false;
  }

  public void split(double x, double y,int count){
    for (PlayerCell playerCell:cells) {
        if(playerCell.getMass() >= GameConstants.MIN_MASS_FOR_EJECT){
          lastSplit = System.currentTimeMillis();
          playerCell.setMass(playerCell.getMass()/2);
          PlayerCell newPlayerCell = new PlayerCell(Cell.idGenerator.next(),playerCell.getX(),playerCell.getY());
          newPlayerCell.updateVelocity(x,y);
          newPlayerCell.setMass(playerCell.getMass());

          newPlayerCell.setVelocity(new DoubleVector(
                  newPlayerCell.getVelocity().getX()*4,
                          newPlayerCell.getVelocity().getX()*4
          )
          );

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
      joining = false;
      log.info("Player {} just now is merged",this.getId());
    }

    if( lastSplit != -1 &&
            System.currentTimeMillis() - lastSplit > GameConstants.MAX_DISCONNECTING_TIME - GameConstants.JOINING_TIME)
      joining = true;
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
}
