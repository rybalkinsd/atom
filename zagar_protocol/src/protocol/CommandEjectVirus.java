package protocol;

import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public final class CommandEjectVirus extends Command {
  @NotNull
  public static final String NAME = "virus";

  public CommandEjectVirus() {
    super(NAME);
  }
}
