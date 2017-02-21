package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

public class Food extends AbstractGameObject{
    @NotNull
    private final Logger log = LogManager.getLogger(Food.class);
    public Food(int _x, int _y, int _radius, Color _color){
        super.x = _x;
        super.y = _y;
        super.radius = _radius;
        super.background = new Color(_color.getRGB());
        if (log.isInfoEnabled()) {
            log.info("New "+this+" created");
        }
    }

    @Override
    public String toString(){
        return "Food{coordinates("+x+","+y+")}";
    }
}
