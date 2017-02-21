package protocol;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author apomosov
 */
public final class CommandEjectMass extends Command implements Serializable{
  @NotNull
  public static final String NAME = "eject";

  private final float dx;
  private final float dy;
  public CommandEjectMass(float dx,float dy) {
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
