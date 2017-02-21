package protocol;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author apomosov
 */
public abstract class Command implements Serializable{
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
