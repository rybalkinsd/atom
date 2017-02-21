package model;

/**
 * @author apomosov
 */
public class PlayerCell
        extends Cell
        implements Eatable,Eater
{
  private final int playerId;

  public PlayerCell(int playerId, int x, int y) {
    super(x, y, GameConstants.DEFAULT_PLAYER_CELL_MASS);
    this.playerId = playerId;
  }

  public int getPlayerId() {
    return playerId;
  }
}
