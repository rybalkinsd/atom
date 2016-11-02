package zagar;

import org.jetbrains.annotations.NotNull;
import zagar.view.GameFrame;

public class Main {
  @NotNull
  public static GameFrame frame;
  @NotNull
  private static Game game;

  public static void main(@NotNull String[] args) {
    GameThread thread = new GameThread();
    frame = new GameFrame();
    game = new Game();

    thread.run();
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void updateGame() {
    try {
      game.tick();
    } catch (Exception e) {
      e.printStackTrace();
    }
    frame.render();
  }
}
