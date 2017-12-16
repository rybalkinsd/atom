package dto;

import geometry.Point;
import objects.Player;

public class PawnDto {
    private Point position;
    private int id;
    private double velocity;
    private int maxBombs;
    private int bombPower;
    private double speedModifier;
    private String type;

    public PawnDto(Player player) {
        position = player.getPosition();
        id = player.getId();
        velocity = player.getSpeed();
        maxBombs = 1;
        bombPower = 1;
        speedModifier = 1;
        type = "Pawn";
    }

    public void set_position(Point point) {
        position = point;
    }

    public void set_id(int playerId) {
        id = playerId;
    }

    public void set_double(double playerVelocity) {
        velocity = playerVelocity;
    }
}
