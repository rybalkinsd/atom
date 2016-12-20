package zagar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class GameThread extends Thread implements Runnable {
  @NotNull
  private static final Logger log = LogManager.getLogger(GameThread.class);
  public GameThread(){
    super("game");
  }
  @Override
  public void run() {
    while (!Thread.interrupted()) {
      long preTickTime = System.currentTimeMillis();
      try{
        Main.updateGame();
      } catch (Throwable e) {
        log.error("Failed to update game",e);
      }
      if (System.currentTimeMillis() % 100 == 0) {
        if( (System.currentTimeMillis() - preTickTime) == 0)
          Game.fps = 1000;
        else
          Game.fps = 1000 / (System.currentTimeMillis() - preTickTime);
        Main.frame.setTitle("· zAgar · " + Game.fps + "fps");
      }
    }
  }
}
