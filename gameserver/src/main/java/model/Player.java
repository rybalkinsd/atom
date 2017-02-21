package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Server player avatar
 * <a href="https://atom.mail.ru/blog/topic/update/39/">HOMEWORK 1</a> example game instance
 *
 * @author Alpi
 */
public class Player  {
  @NotNull
  private static final Logger log = LogManager.getLogger(Player.class);
  @NotNull
  private String name;

  private List<PlayerObject> playerBody = new ArrayList<>();
  //TODO maybe we need something else here?

  /**
   * Create new Player
   *
   * @param name        visible name
   */
  public Player(@NotNull String name) {
    playerBody.add(new PlayerObject());

    this.name = name;
    if (log.isInfoEnabled()) {
      log.info(toString() + " created");
    }
  }

  public List<PlayerObject> getPlayerBody(){return new ArrayList<>(playerBody);}

  @Override
  public String toString() {
    return  playerBody.get(0).toString()+
            "Player{" +
            "name='" + name + '\'' +
            '}';
  }
}