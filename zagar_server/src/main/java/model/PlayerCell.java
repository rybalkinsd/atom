package model;

/**
 * Created by User on 10.10.2016.
 */

//Игровая сущность, управляемая игроком
public class PlayerCell extends Cell {

    private final int id;
    private final String name;

    public PlayerCell(int id, Location location, String name) {
        super(location, GameConstants.DEFAULT_PLAYER_CELL_MASS);
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName(){return name;}

}
