package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.net.URL;

//part of graphic veiw of player (circle)
public class GraphicPartOfPlayer extends AbstractGameObject{
    @NotNull
    private static final Logger log = LogManager.getLogger(GraphicPartOfPlayer.class);
    @NotNull
    private String text;

    public GraphicPartOfPlayer(@NotNull String _text){
        super.x = GameConstants.DEFAULT_START_PLAYER_X_COORD;
        super.y = GameConstants.DEFAULT_START_PLAYER_Y_COORD;
        super.radius = GameConstants.START_PLAYER_RADIUS;
        super.background = new Color(GameConstants.DEFAULT_PLAYER_COLOR);
        text = _text;
        if (log.isInfoEnabled()) {
            log.info( "View of " + toString() + " created");
        }
    }

    public GraphicPartOfPlayer(int _x, int _y, int _radius, int colorRGB, @NotNull String _text){
        super.x = _x;
        super.y = _y;
        super.radius = _radius;
        super.background = new Color(colorRGB);
        text = _text;
        if (log.isInfoEnabled()) {
            log.info( "View of " + toString() + " created");
        }
    }

    public GraphicPartOfPlayer(int _x, int _y, int _radius, @NotNull String imageURL, @NotNull String _text){
        super.x = _x;
        super.y = _y;
        super.radius = _radius;
        try {
            super.background = ImageIO.read(new URL(imageURL));
            text = _text;
        }
        catch(Exception e){
            if (log.isInfoEnabled()) {
                log.info("In creation of view of " + toString() + " was catched exception \""+
                e.getMessage()+"\"");
            }
            //if url of image is not found player has a default color
            super.background = new Color(GameConstants.DEFAULT_PLAYER_COLOR);
            if (log.isInfoEnabled()) {
                log.info("View of " + toString() + " created with default color");
            }
        }
        if (log.isInfoEnabled()) {
            log.info("View of " + toString() + " created");
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + text + '\'' +
                '}';
    }
}
