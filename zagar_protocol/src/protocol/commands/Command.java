package protocol.commands;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public abstract class Command {
  @NotNull
  @Expose
  private final String command;

  protected Command(@NotNull String name) {
    this.command = name;
  }

  @NotNull
  public String getCommand() {
    return command;
  }
}
