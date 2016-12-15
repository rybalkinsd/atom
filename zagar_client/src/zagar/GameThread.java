package zagar;

public class GameThread extends Thread implements Runnable {
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
        e.printStackTrace();
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
