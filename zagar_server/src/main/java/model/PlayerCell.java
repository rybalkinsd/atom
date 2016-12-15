package model;

/**
 * @author apomosov
 */
public class PlayerCell extends Cell {
  private final int id;
  private DoubleVector velocity = new DoubleVector();
  private boolean ungovernable;
  private double becameUngovernable;

  public PlayerCell(int id, int x, int y) {
    super(x, y, GameConstants.DEFAULT_PLAYER_CELL_MASS);
    this.id = id;
    ungovernable = false;
  }

  public int getId() {
    return id;
  }

  public DoubleVector getVelocity(){return velocity;}
  public void setVelocity(DoubleVector velocity){this.velocity = velocity;}

  public boolean getUngovernable(){return ungovernable;}
  public void setUngovernable(boolean ungovernable){
    this.ungovernable = ungovernable;
    becameUngovernable = System.currentTimeMillis();
  }

  public void updateVelocity(double x, double y){
    if(ungovernable){
      if(System.currentTimeMillis() - becameUngovernable > GameConstants.UNGOVERNABLE_TIME)
        ungovernable = false;
      return;
    }

    double dx = x-getX();
    double dy = y-getY();

    if (Math.abs(dx) <= getMass()/5 && Math.abs(dy) <= getMass()/5){
      setVelocity(new DoubleVector(0,0));
      return;
    }

    double r = Math.sqrt(dx*dx + dy*dy);

    dx = dx / r;
    dy = dy / r;



    setVelocity(
            new DoubleVector(
                    dx * (0.5 + 1/getMass()),
                    dy * (0.5 + 1/getMass()))
    );

    if (new Double(getVelocity().getX()).equals(Double.NaN) ||
            new Double(getVelocity().getY()).equals( Double.NaN))
                setVelocity(new DoubleVector(0, 0));

  }

  public boolean pushOff(PlayerCell playerCell){
    double r = Math.sqrt(
            (getX() - playerCell.getX())*(getX() - playerCell.getX())+
                    (getY() - playerCell.getY())*(getY() - playerCell.getY())
            );

    return r < getMass() + playerCell.getMass() && !playerCell.getUngovernable();

  }
}
