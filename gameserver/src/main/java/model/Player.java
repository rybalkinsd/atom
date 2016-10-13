package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

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
  @NotNull
  private String id;
  private int foodEaten;
  private LocalDateTime startTime;
  //private LocalDateTime timeAlive;
  private int cellsEaten;
  private int highestMass;
  //leaderboardTime, topPosition - for multiplay

  /**
   * Create new Player
   *
   * @param name        visible name
   *                    Sets random id
   *                    foodEaten - amount of eaten pellets
   *                    cellsEaten - amount of another players eaten
   *
   */
  public Player(@NotNull String name) {

    this.name = name;
    this.setRandomId();
    this.foodEaten = 0;
    this.startTime = LocalDateTime.now();
    this.cellsEaten = 0;
    this.highestMass = 0;

    if (log.isInfoEnabled()) {
      log.info(toString() + " created");
    }
  }

  //setters
  private void setFoodEaten(int num){
    this.foodEaten=num;
  }
  private void setCellsEaten(int num){
    this.cellsEaten=num;
  }

  /**
   * Set the highest mass, compares current mass with previous highest
   * @param mass  current mass
   */
  private void sethighestMass(int mass){
    if(this.highestMass<mass)
      this.highestMass=mass;
  }

  //getters
  public LocalDateTime getStartTime(){
    return this.startTime;
  }
  public int getFoodEaten(){
    return this.foodEaten;
  }
  public int getCellsEaten(){
    return this.cellsEaten;
  }
  public int getHighestMass(){
    return this.highestMass;
  }

  public String getName(){
    return this.name;
  }

  /**
   * Ugly fix for generating id
   */
  private void setRandomId(){
    String uniqueID = UUID.randomUUID().toString();
    this.id=uniqueID;
  }

  @Override
  public String toString() {
    return "Player{" +
            "name='" + this.name + '\'' + "/startTime=" + this.getStartTime() +
            '}';
  }
}