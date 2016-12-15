package protocol;

import org.jetbrains.annotations.NotNull;

public abstract class Command {
  @NotNull
  private final String command;

  protected Command(@NotNull String name) {
    this.command = name;
  }

  @NotNull
  public String getCommand() {
    return command;
  }
}