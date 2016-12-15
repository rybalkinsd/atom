package zagar;

import org.jetbrains.annotations.NotNull;

public interface GameConstants {
  @NotNull
  String DEFAULT_GAME_SERVER_HOST = "localhost";
  int DEFAULT_GAME_SERVER_PORT = 7000;
  @NotNull
  String DEFAULT_ACCOUNT_SERVER_HOST = "localhost";
  int DEFAULT_ACCOUNT_SERVER_PORT = 8080;
  @NotNull
  String DEFAULT_LOGIN = "zAgar";
  @NotNull
  String DEFAULT_PASSWORD = "pass";
  int SPEED_SCALE_FACTOR = 1000;
}