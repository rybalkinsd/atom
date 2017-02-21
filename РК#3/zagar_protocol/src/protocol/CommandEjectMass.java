package protocol;

import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public final class CommandEjectMass extends Command {
  @NotNull
  public static final String NAME = "eject";

  public CommandEjectMass() {
    super(NAME);
  }
}
