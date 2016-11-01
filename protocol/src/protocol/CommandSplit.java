package protocol;

import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public final class CommandSplit extends Command {
  @NotNull
  public static final String NAME = "split";

  public CommandSplit() {
    super(NAME);
  }
}
