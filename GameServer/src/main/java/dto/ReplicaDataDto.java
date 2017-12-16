package dto;

import java.util.ArrayList;

public class ReplicaDataDto {
    ArrayList<Object> objects;
    boolean gameOver = false;

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setObjects(ArrayList<Object> objects) {
        this.objects = objects;
    }
}
