package model;

/**
 * Created by venik on 08.12.16.
 */
public class SplitFood extends Food {
    private DoubleVector velocity = new DoubleVector();
    private long emergeTime;
    private double lastUpdate;

    public SplitFood(int x, int y) {
        super(x, y);
        emergeTime = System.currentTimeMillis();
        lastUpdate = System.currentTimeMillis();
    }

    public DoubleVector getVelocity(){return velocity;}
    public void setVelocity(DoubleVector velocity){this.velocity = velocity;}
    public boolean update(){
        if(System.currentTimeMillis() - emergeTime > GameConstants.SPLIT_FOOD_LIFETIME)
            return true;
        double dTime = System.currentTimeMillis() - lastUpdate;

        int newX = (int)(getX() + GameConstants.SPLIT_VELOCITY*dTime*getVelocity().getX());
        int newY = (int)(getY() + GameConstants.SPLIT_VELOCITY*dTime*getVelocity().getY());

        if (newX < 0){
            newX = 0;
            velocity = DoubleVector.zero();
        }
        if (newX > GameConstants.FIELD_WIDTH) {
            newX = GameConstants.FIELD_WIDTH;
            velocity = DoubleVector.zero();
        }

        if (newY < 0){
            newY = 0;
            velocity = DoubleVector.zero();
        }
        if (newY > GameConstants.FIELD_HEIGHT) {
            newY = GameConstants.FIELD_HEIGHT;
            velocity = DoubleVector.zero();
        }

        setX(newX);
        setY(newY);

        lastUpdate = System.currentTimeMillis();
        return false;
    }
}
