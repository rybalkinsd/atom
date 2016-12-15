package model;

import org.jetbrains.annotations.NotNull;
import java.util.List;

public interface GameSession {
  void join(@NotNull Player player);
  void leave(@NotNull Player player);
  List<Player> getPlayers();
  Field getField();
}