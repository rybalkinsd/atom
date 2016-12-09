package zagar;

public class GameThread extends Thread implements Runnable {
  public GameThread() {
    super("game");
  }

  @Override
  public void run() {
    while (true) {
      long preTickTime = System.currentTimeMillis();
      Main.updateGame();
      if (System.currentTimeMillis() % 100 == 0) {
        Game.fps = 1000 / (System.currentTimeMillis() - preTickTime + 1);
        Main.frame.setTitle("· zAgar · " + Game.fps + "fps");
      }
    }
  }
}
