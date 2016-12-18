package main.java.zagar;

import main.java.zagar.view.GameFrame;
import org.jetbrains.annotations.NotNull;

public class Main {
  @NotNull
  private static GameFrame frame = new GameFrame();
  @NotNull
  private static Game game = new Game();

  public static void main(@NotNull String[] args) {
    GameThread thread = new GameThread();
    thread.start();
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @NotNull
  public static GameFrame getFrame() {
    return frame;
  }

  static synchronized void updateGame() {
    try {
        game.tick();
        frame.render();
    } catch (Exception e) {
        e.printStackTrace();
    }
  }
}
