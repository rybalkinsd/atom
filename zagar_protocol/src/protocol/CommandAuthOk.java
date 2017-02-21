package protocol;

import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public final class CommandAuthOk extends Command {
  @NotNull
  public static final String NAME = "auth_ok";

  public int playerID;

  @Deprecated
  public CommandAuthOk() {
    super(NAME);
  }

  public CommandAuthOk(int playerID) {
    super(NAME);
    this.playerID = playerID;
  }
}
