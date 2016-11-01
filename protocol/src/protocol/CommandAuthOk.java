package protocol;

import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public final class CommandAuthOk extends Command {
  @NotNull
  public static final String NAME = "auth_ok";

  public CommandAuthOk() {
    super(NAME);
  }
}
