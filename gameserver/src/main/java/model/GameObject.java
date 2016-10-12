package model;

import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static java.lang.Math.random;
import static java.lang.Math.round;

/**
 * Created by svuatoslav on 10/11/16.
 */
public abstract class GameObject {
    private Pair<Double,Double> location;
    private Color color;
    @NotNull
    private static final Logger log = LogManager.getLogger(GameObject.class);

    public GameObject() {
        color = Color.rgb(((Double)(random()*256)).intValue(),((Double)(random()*256)).intValue(),((Double)(random()*256)).intValue());
    }

    public GameObject(Pair<Double,Double> point) {
        color = Color.rgb(((Double)(random()*256)).intValue(),((Double)(random()*256)).intValue(),((Double)(random()*256)).intValue());
        location = new Pair<Double,Double>(point.getKey(),point.getValue());
    }

    public int writeLog(String info)
    {
        if (log.isInfoEnabled()) {
            log.info(this + " " + info);
            return 0;
        }
        return 1;
    }

    public Pair<Double,Double> getLocation ()
    {
        return new Pair<Double,Double>(location.getKey(),location.getValue());
    }

    public Color getColor(){return color;}

    public String getParam()
    {
        return ((color ==null)?"":("Color=<" + color.getRed() + ',' + color.getGreen() + ',' + color.getBlue()+'>')) +
                ((location==null)?"":("location=<" + location.getKey() +',' + location.getValue() + '>'));
    }

    public void setLocation(Pair<Double,Double> Location)
    {
        location=new Pair<Double,Double>(Location.getKey(),Location.getValue());
        if (log.isInfoEnabled()) {
            log.info(toString() + " moved");
        }
    }

    @Override
    public String toString()
    {
        return "GameObject{" +
                getParam() +
                '}';
    }
}
