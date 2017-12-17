package bomber.connectionhandler.json;

import bomber.games.model.GameObject;

import java.util.List;

public class DataReplica {
    private List<? extends GameObject> objects;
    private boolean gameOver = false;


    public DataReplica() {
    }

    public List<? extends GameObject> getObjects() {
        return objects;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setObjects(List<? extends GameObject> objects) {
        this.objects = objects;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }


}
