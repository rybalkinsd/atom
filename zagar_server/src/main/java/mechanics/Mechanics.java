package mechanics;

import main.ApplicationContext;
import main.Service;
import matchmaker.MatchMaker;
import matchmaker.MatchMakerImpl;
import messageSystem.Message;
import messageSystem.MessageSystem;
import messageSystem.messages.ReplicateMsg;
import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import protocol.CommandEjectMass;
import protocol.CommandMove;
import protocol.CommandSplit;
import ticker.Tickable;
import ticker.Ticker;

/**
 * Created by apomosov on 14.05.16.
 */
public class Mechanics extends Service implements Tickable {
  @NotNull
  private final static Logger log = LogManager.getLogger(Mechanics.class);

  public Mechanics() {
    super("mechanics");
  }

  @Override
  public void run() {
    log.info(getAddress() + " started");
    try {
      Thread.sleep(2_000);
    }catch (Exception ignored){}
    Ticker ticker = new Ticker(this,50);
    ticker.loop();
  }

  @Override
  public void tick(long elapsedNanos) {
    try {
      Thread.sleep(20);
    } catch (InterruptedException e) {
      log.error(e);
      Thread.currentThread().interrupt();
    }
    ApplicationContext.instance().get(MatchMaker.class).checkUnactive();
    eatAll();
    splitAll();
    //   log.info("Start replication");
    @NotNull MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);
    Message message = new ReplicateMsg(this.getAddress());
    messageSystem.sendMessage(message);

    //execute all messages from queue
    messageSystem.execForService(this);
  }

  private void splitAll() {
    for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {

      for (Player player : gameSession.getPlayers()) {
        for (SplitFood splitFood : player.getSplitFoodSet())
          gameSession.getField().addSplitFood(splitFood);
        player.getSplitFoodSet().clear();
      }

      for(SplitFood splitFood:gameSession.getField().getSplitFoodSet()){

        if(splitFood.update()){
          gameSession.getField().addFood(new model.Food(splitFood.getX(),splitFood.getY()));
          gameSession.getField().getSplitFoodSet().remove(splitFood);
        }
      }

    }
  }

  private void eatAll() {
    for (GameSession gameSession : ApplicationContext.instance().get(MatchMaker.class).getActiveGameSessions()) {
      //ArrayList<model.Food> foodArrayList = new ArrayList<>();

      //try to eat another Players
      for (Player player : gameSession.getPlayers())
        for(Player enemy: gameSession.getPlayers())
          for (PlayerCell playerCell:enemy.getCells())
            if(player.eat(playerCell))
              //foodArrayList.add(food);
            {
              enemy.getCells().remove(playerCell);
            }

      //try to eat Food
      for (Player player : gameSession.getPlayers())
        for(model.Food food:gameSession.getField().getFoodSet())
          if(player.eat(food))
            //foodArrayList.add(food);
            gameSession.getField().getFoodSet().remove(food);

      //try to eat Virus
      for (Player player : gameSession.getPlayers())
        for(model.Virus virus:gameSession.getField().getViruses())
          if(player.eat(virus))
            //foodArrayList.add(food);
            gameSession.getField().getViruses().remove(virus);

      //for(model.Food food:foodArrayList) {
        //gameSession.getField().getFoodSet().remove(food);
      //}

      for (Player player : gameSession.getPlayers())
        if (player.isRespawn()) {
          gameSession.respawn(player);
          player.setRespawn(false);
      }

    }
  }

  public void EjectMass (@NotNull  Player player,@NotNull CommandEjectMass commandEjectMass)
  {
    player.eject(commandEjectMass.getDx(),commandEjectMass.getDy());
    log.debug("{} wants to eject mass  <{},{}> (in thread {})",player,commandEjectMass.getDx(),commandEjectMass.getDy(),Thread.currentThread());
  }

  public void Move (@NotNull Player player, @NotNull CommandMove commandMove)
  {
    player.move(commandMove.getDx(),commandMove.getDy());
    log.debug("{} wants to move <{},{}> (in thread {})",player,commandMove.getDx(),commandMove.getDy(),Thread.currentThread());
  }

  public void Split (@NotNull Player player, @NotNull CommandSplit commandSplit)
  {
    player.split(commandSplit.getDx(),commandSplit.getDy());
    log.debug("{} wants to split <{},{}>(in thread {})",player,commandSplit.getDx(),commandSplit.getDy(),Thread.currentThread());
  }

  public void Respawn (@NotNull Player player){
    player.setRespawn(true);
    log.info("{} wants to respawn (in thread {})",player.getName(),Thread.currentThread());
  }
}
