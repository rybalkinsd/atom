package zagar;

import org.jetbrains.annotations.NotNull;
import zagar.ticker.Tickable;
import zagar.view.GameFrame;

public class Main implements Tickable {
  @NotNull
  public static GameFrame frame;
  @NotNull
  private static Game game;

  public static void main(@NotNull String[] args) {
    GameThread thread = new GameThread();
    frame = new GameFrame();
    game = new Game();

    thread.start();
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static synchronized void updateGame() {
    try {
      game.tick();
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      frame.render();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void tick(long elapsedNanos) {
    updateGame();
  }
}
