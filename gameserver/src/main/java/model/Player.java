package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;
import java.util.List;

import static model.GameConstants.INITIAL_SIZE;

/**
 * Server player avatar
 * <a href="https://atom.mail.ru/blog/topic/update/39/">HOMEWORK 1</a> example game instance
 *
 * @author Alpi
 */
public class Player {
  @NotNull
  private static final Logger log = LogManager.getLogger(Player.class);
  @NotNull
  private String name;
  List<PlayerBody> bodies;
  //TODO maybe we need something else here?

  /**
   * Create new Player
   *
   * @param name        visible name
   */
  public Player(@NotNull String name) {
    this.name = name;
    bodies = new ArrayList<PlayerBody>();
    if (log.isInfoEnabled()) {
      log.info(toString() + " created");
    }
  }

  public long getScore()
  {
    long res=0;
    for(Iterator<PlayerBody> i = bodies.listIterator();i.hasNext();)
    {
      res+=i.next().getSize();
    }
    return res;
  }

  public String getName()
  {
    return new String(name);
  }

  public void addBody(PlayerBody body)
  {bodies.add(body);}

  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' +
            "score='" + getScore() + '\'' +
        '}';
  }
}
