package protocol;

import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public final class CommandAuthFail extends Command {
  @NotNull
  public static final String NAME = "auth_fail";

  @NotNull
  private final String cause;

  public CommandAuthFail(@NotNull String login, @NotNull String token, @NotNull String cause) {
    super(NAME);
    this.cause = cause;
  }

  @NotNull
  public String getCause() {
    return cause;
  }
}
