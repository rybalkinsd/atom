package zagar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import zagar.view.GameFrame;

public class Main {
  @NotNull
  private static final Logger log = LogManager.getLogger(GameThread.class);
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
    } catch (InterruptedException ignored) {
    }
  }

  public static void updateGame() {
    try {
      game.tick();
    } catch (Exception e) {
      log.error("Failed to tick in game",e);
    }
    frame.render();
  }
}
