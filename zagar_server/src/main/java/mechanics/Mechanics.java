package mechanics;

import main.ApplicationContext;
import main.Service;
import matchmaker.IMatchMaker;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.ReplicateMsg;
import model.Cell;
import model.GameSession;
import model.Player;
import model.PlayerCell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ticker.Tickable;
import ticker.Ticker;

import java.util.*;

import static java.lang.Math.*;
import static mechanics.MechanicConstants.*;

/**
 * Created by apomosov on 14.05.16.
 */
public class Mechanics extends Service implements Tickable {
  @NotNull
  private final static Logger log = LogManager.getLogger(Mechanics.class);
  @NotNull
  private final Map<Integer,Float[]> playerMoves = new HashMap<>();
  @NotNull
  private final List<Integer> playerEject = new ArrayList<>();
  @NotNull
  private final List<Integer> playerSplit = new ArrayList<>();

  public Mechanics() {
    super("mechanics");
  }

  @Override
  public void run() {
    log.info(getAddress() + " started");
    Ticker ticker = new Ticker(this, 40);
    ticker.loop();
  }

  private static void decrementSpeed(Cell cell, float dt){
    cell.setSpeedX( (cell.getSpeedX() > MINIMAL_SPEED)? cell.getSpeedX()*(1 - 10*dt/VISCOSITY_DECREMENT) : 0.0f );
    cell.setSpeedY( (cell.getSpeedY() > MINIMAL_SPEED)? cell.getSpeedY()*(1 - 10*dt/VISCOSITY_DECREMENT) : 0.0f );
  }

  private static void computeCoordinates(Cell cell, float dt){
    cell.setX(cell.getX() + (int)(cell.getSpeedX()*dt));
    cell.setY(cell.getY() + (int)(cell.getSpeedY()*dt));
  }

  @Override
  public void tick(long elapsedNanos) {
    float dT = elapsedNanos/1000_000f;
    log.debug("DT =  " + dT + " millis");

    //TODO mechanics
    for (GameSession gs : ApplicationContext.instance().get(IMatchMaker.class).getActiveGameSessions()){

      gs.getFoodGenerator().tick(elapsedNanos);
      log.debug("FOOD " + gs.getField().getFoods().size());
      gs.getVirusGenerator().generate();
      log.debug("VIRUSES " + gs.getField().getViruses());

      for (Cell cell : gs.getField().getFreeCells()){
        decrementSpeed(cell, dT);
        computeCoordinates(cell, dT);
      }



      for (Player player : gs.getPlayers()){
        // moves
        if(playerMoves.containsKey(player.getId())){
          float vX = playerMoves.get(player.getId())[0]/1000; // [ dx/millis ]
          float vY = playerMoves.get(player.getId())[1]/1000; // [ dy/millis ]
          log.debug(String.format("MOVING PLAYER '%s' TO (%f,%f)",player.getName(),vX,vY));

          float avgX = 0;
          float avgY = 0;

//          float dX = (vX/10)*(dT/TIME_FACTOR)*gs.getField().getHeight();
//          float dY = (vY/10)*(dT/TIME_FACTOR)*gs.getField().getHeight();

//          log.debug(String.format("DX = %f; DY = %f", dX, dY));

          for (Cell c : player.getCells()){
            avgX += (float) c.getX()/player.getCells().size();
            avgY += (float) c.getY()/player.getCells().size();
          }

//          avgX += dX; avgY += dY;

          for (Cell cell : player.getCells()){
              if(cell.getSpeedX() > MAXIMAL_SPEED*1.2 || cell.getSpeedY() > MAXIMAL_SPEED*1.2){
                decrementSpeed(cell, dT);
              }
              else {
                float speedX = (vX + (avgX - cell.getX())/ATTRACTION_DECREMENT)*VISCOSITY_DECREMENT;
                float speedY = (vY + (avgY - cell.getY())/ATTRACTION_DECREMENT)*VISCOSITY_DECREMENT;

                cell.setSpeedX((speedX > MAXIMAL_SPEED)? MAXIMAL_SPEED : speedX);
                cell.setSpeedY((speedY > MAXIMAL_SPEED)? MAXIMAL_SPEED : speedY);
              }

              computeCoordinates(cell, dT);


            // eating food
            for(Cell food : new HashSet<>(gs.getField().getFoods())){
              if(food.distance(cell) <= Math.abs(cell.getRadius() - food.getRadius())){
                cell.setMass(cell.getMass() + food.getMass());
                log.debug("PLAYER " + player + " eat food");
                gs.getField().getFoods().remove(food);
              }
            }
          }
        }

        // eject
        if (playerEject.contains(player.getId())) {

          for (Cell cell : player.getCells()) {
            int initMass = cell.getMass();
            if (initMass >= MINIMAL_MASS + EJECTED_MASS) {

                cell.setMass(initMass - EJECTED_MASS);

                int x = cell.getX() + cell.getRadius() + 50;
                int y = cell.getY() + cell.getRadius() + 50;

                Cell ejectedCell = new PlayerCell(-1, x, y);
                ejectedCell.setSpeedX(cell.getSpeedX() + 5);
                ejectedCell.setSpeedY(cell.getSpeedY() + 5);
                ejectedCell.setMass(EJECTED_MASS);

                gs.getField().setFreeCells(ejectedCell);
            }
          }
        }

          // split
          if (playerSplit.contains(player.getId())) {
            for (Cell cell : new ArrayList<>(player.getCells())) {
              int initMass = cell.getMass();
              if (initMass >= 2*MINIMAL_MASS) {
                float angle = (float) (2*Math.PI*Math.random());
                float dVx = (float)(SPLIT_SPEED*cos(angle));
                float dVy = (float)(SPLIT_SPEED*sin(angle));
                int halfMass = round(initMass/2);
                cell.setMass(halfMass);
                cell.setSpeedX(cell.getSpeedX() + dVx);
                cell.setSpeedY(cell.getSpeedY() + dVy);

                PlayerCell newCell = new PlayerCell(player.getId(),cell.getX(), cell.getY());
                newCell.setMass(halfMass);
                player.addCell(newCell);
                newCell.setSpeedX(cell.getSpeedX() - dVx);
                newCell.setSpeedY(cell.getSpeedY() - dVy);
              }
            }
          }


        player.updateScore();
      }

//      for (Cell cell : notNullSpeedCells) {
//        cell.setSpeedX(round(cell.getSpeedX()/1.2f));
//        cell.setSpeedY(round(cell.getSpeedY()/1.2f));
//      }
    }

    @NotNull MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);

    Message replicateMsg = new ReplicateMsg(this.getAddress());

    log.info("Start replication");
    messageSystem.sendMessage(replicateMsg);

    playerSplit.clear();
    playerEject.clear();
    playerMoves.clear();
    //execute all messages from queue
    messageSystem.execForService(this);
  }

  public void move(Player player, float dx, float dy){
    Float[] move = playerMoves.get(player.getId());
    if(move == null){
      playerMoves.put(player.getId(), new Float[]{dx,dy});
    }
    else {
      move[0] = dx; move[1] = dy;
    }
    log.debug(player + " is about to move");
  }

  public void eject(Player player){
    int id = player.getId();
    if (!playerEject.contains(id)) {
      playerEject.add(id);
    }
    log.debug(player + " is about to eject");
  }

  public void split(Player player){
    int id = player.getId();
    if (!playerSplit.contains(id)) {
        playerSplit.add(id);
    }
    log.debug(player + " is about to split");
  }
}
