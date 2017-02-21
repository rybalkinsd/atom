package main.java.zagar;

class GameThread extends Thread implements Runnable {
  GameThread() {
    super("game");
  }
  @Override
  public void run() {
    while (!Thread.interrupted()) {
      long preTickTime = System.currentTimeMillis();
      Main.updateGame();
      if (System.currentTimeMillis() % 100 == 0) {
        Game.fps = 1000 / (System.currentTimeMillis() - preTickTime + 1);
        Main.getFrame().setTitle("· zAgar · " + Game.fps + "fps");
      }
    }
  }
}
