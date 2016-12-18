package protocol.commands;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;

/**
 * @author apomosov
 */
public final class CommandMove extends Command {
  @NotNull
  public static final String NAME = "move";

  @Expose
  private final float dx;
  @Expose
  private final float dy;
  public CommandMove(float dx, float dy) {
    super(NAME);
    this.dx = dx;
    this.dy = dy;
  }

  public float getDx() {
    return dx;
  }

  public float getDy() {
    return dy;
  }
}
