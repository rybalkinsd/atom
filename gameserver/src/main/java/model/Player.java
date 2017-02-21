package model;

import model.units.UnitPlayer;
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
public class Player {
  @NotNull
  private static final Logger log = LogManager.getLogger(Player.class);
  @NotNull
  private String name;

  private ArrayList<UnitPlayer> playerUnits;
  private int color;

  /**
   * Create new Player
   *
   * @param name        visible name
   */
  public Player(@NotNull String name) {
    this.name = name;
    if (log.isInfoEnabled()) {
      log.info(toString() + " created");
    }
  }

  public void attack(){
    ArrayList<UnitPlayer> nUnits = new ArrayList<>();
    for (int i = 0; i < playerUnits.size(); i++) {
      nUnits.add(playerUnits.get(i));
    }
    playerUnits.addAll(nUnits);
  }

  @NotNull
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Player{" +
        "name='" + name + '\'' +
        '}';
  }
}
