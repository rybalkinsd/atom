package model;

import org.jetbrains.annotations.NotNull;
import utils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author apomosov
 */
public class GameSessionImpl implements GameSession {
  private static final IDGenerator idGenerator = new SequentialIDGenerator();
  private final int id = idGenerator.next();
  @NotNull
  private final Field field;
  @NotNull
  private final List<Player> players = new ArrayList<>();
  @NotNull
  private final FoodGenerator foodGenerator;
  @NotNull
  private final PlayerPlacer playerPlacer;
  @NotNull
  private final VirusGenerator virusGenerator;

  public GameSessionImpl(@NotNull Field field, @NotNull FoodGenerator foodGenerator, @NotNull PlayerPlacer playerPlacer, @NotNull VirusGenerator virusGenerator) {
    this.field = field;
    this.foodGenerator = foodGenerator;
    this.playerPlacer = playerPlacer;
    this.virusGenerator = virusGenerator;
    virusGenerator.generate();
  }

  @Override
  public void join(@NotNull Player player) {
    players.add(player);
    this.playerPlacer.place(player);
  }

  @Override
  public void leave(@NotNull Player player) {
    players.remove(player);
  }

  @Override
  public List<Player> getPlayers() {
    return new ArrayList<>(players);
  }

  @Override
  public Field getField() {
    return field;
  }

  @Override
  public String toString() {
    return "GameSessionImpl{" +
        "id=" + id +
        '}';
  }

  public void update(){
    EatComparator comparator = new EatComparator();
    for (Player player: players){
      for (PlayerCell cell: player.getCells()){
        for (Player player1: players){
          if (player != player1){
            for (PlayerCell cell1: player1.getCells()){
              if ((Math.pow(cell.getX() - cell1.getX(), 2)+ Math.pow(cell.getY() - cell1.getY(), 2) <= cell.getRadius()) && (comparator.compare(cell, cell1) > 0)){
                cell.setMass(cell.getMass() + cell1.getMass());
                player1.removeCell(cell1);
                break;
              }
            }
          }
        }
        for (Food food: field.getFoods()){
          if ((Math.pow(cell.getX() - food.getX(), 2)+ Math.pow(cell.getY() - food.getY(), 2) <= cell.getRadius())){
            cell.setMass(cell.getMass() + food.getMass());
            field.getFoods().remove(food);
          }
        }
        if (cell.getMass() <= 20) {
          for (Virus virus : field.getViruses()) {
            if ((Math.pow(cell.getX() - virus.getX(), 2) + Math.pow(cell.getY() - virus.getY(), 2) <= cell.getRadius())) {
              cell.setMass(cell.getMass() - virus.getMass());
              field.getViruses().remove(virus);
            }
          }
        }
      }
    }
    for (Player player: players){
      if (player.getIsRespawnable()){
        player.addCell(new PlayerCell(PlayerCell.idGenerator.next(),0,0));
        this.playerPlacer.place(player);
      }
    }
  }
}
