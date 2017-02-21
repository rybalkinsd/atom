package protocol.commands;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public final class CommandAuth extends Command {
  @NotNull
  public static final String NAME = "auth";
  @NotNull
  @Expose
  private final String token;
  @NotNull
  @Expose
  private final String login;

  public CommandAuth(@NotNull String login, @NotNull String token) {
    super(NAME);
    this.token = token;
    this.login = login;
  }

  @NotNull
  public String getToken() {
    return token;
  }

  @NotNull
  public String getLogin() {
    return login;
  }
}
