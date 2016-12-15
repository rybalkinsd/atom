package protocol;

import org.jetbrains.annotations.NotNull;

public final class CommandAuthOk extends Command {
  @NotNull
  public static final String NAME = "auth_ok";

    private final int playerID;

  public CommandAuthOk(int id) {
    super(NAME);
      playerID = id;
  }

  public int getPlayerID(){ return playerID; }
}