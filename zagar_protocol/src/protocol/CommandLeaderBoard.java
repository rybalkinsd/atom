package protocol;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author apomosov
 */
public final class  CommandLeaderBoard extends Command implements Serializable {
  @NotNull
  public static final String NAME = "leader_board";

  @NotNull
  private final String[] leaderBoard;

  public CommandLeaderBoard(@NotNull String[] leaderBoard) {
    super(NAME);
    this.leaderBoard = leaderBoard;
  }

  @NotNull
  public String[] getLeaderBoard() {
    return leaderBoard;
  }
}
