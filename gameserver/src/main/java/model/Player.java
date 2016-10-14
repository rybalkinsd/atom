package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Server player avatar
 * <a href="https://atom.mail.ru/blog/topic/update/39/">HOMEWORK 1</a> example game instance
 *
 * @author Alpi
 */
public class Player{
  @NotNull
  private static final Logger log = LogManager.getLogger(Player.class);
  @NotNull
  private ArrayList<GraphicPartOfPlayer> parts; //graphic veiw of player and coordinates of all his parts
  @NotNull
  private String name;
  /**
   * Create new Player
   *
   * @param name        visible name
   */

  public Player(@NotNull String _name) {
    this.name = _name;
    parts = new ArrayList<>(0);
    parts.add(new GraphicPartOfPlayer(name));
    if (log.isInfoEnabled()) {
      log.info(toString() + " created");
    }
  }

  public Player(int _x, int _y, int _radius, int colorRGB, @NotNull String _name) {
    this.name = _name;
    parts = new ArrayList<>(0);
    parts.add(new GraphicPartOfPlayer(_x,_y,_radius,colorRGB, name));
    if (log.isInfoEnabled()) {
      log.info(toString() + " created");
    }
  }

  public Player(int _x, int _y, int _radius, @NotNull String _imageURL, @NotNull String _name) {
    this.name = _name;
    parts = new ArrayList<>(0);
    parts.add(new GraphicPartOfPlayer(_x,_y,_radius,_imageURL,name));
    if (log.isInfoEnabled()) {
      log.info(toString() + " created");
    }
  }

  @Override
  public String toString() {
    return "Player{" +
            "name='" + name + '\'' +
            '}';
  }
}
