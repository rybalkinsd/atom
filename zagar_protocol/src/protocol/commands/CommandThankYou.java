package protocol.commands;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public final class CommandThankYou extends Command {
  @NotNull
  public static final String NAME = "thankyou";
  @NotNull
  @Expose
  private final String name;

  public CommandThankYou(@NotNull String name) {
    super(NAME);
    this.name = name;
  }

  @NotNull
  public String getName() {
    return name;
  }
}
