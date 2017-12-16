package dto;

import geometry.Point;
import objects.Bomb;
import objects.Fire;
import objects.Wall;
import objects.Wood;

public class ObjectDto {
    private Point position;
    private int id;
    private String type;

    public ObjectDto(Wall wall) {
        position = wall.getPosition();
        id = wall.getId() + 100;
        type = "Wall";
    }

    public ObjectDto(Wood wood) {
        position = wood.getPosition();
        id = wood.getId() + 400;
        type = "Wood";
    }

    public ObjectDto(Fire fire) {
        position = fire.getPosition();
        id = fire.getId() + 800;
        type = "Fire";
    }

    public ObjectDto(Bomb bomb) {
        position = bomb.getPosition();
        id = bomb.getId() + 1000;
        type = "Bomb";
    }

    public void setPosition(Point point) {
        position = point;
    }

    public void setType(String obType) {
        type = obType;
    }

    public void setId(int obId) {
        id = obId;
    }

}
