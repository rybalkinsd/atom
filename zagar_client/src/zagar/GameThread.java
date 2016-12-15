package zagar;

public class GameThread extends Thread implements Runnable {
  public GameThread() {
    super("game");
  }

  @Override
  public void run() {
    while (true) {
      try {
        Thread.currentThread().sleep(50);
      } catch (Exception e){
        e.printStackTrace();
      }
      Main.updateGame();
    }
  }
}