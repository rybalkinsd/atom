package dto;

import java.util.ArrayList;

public class ReplicaDto {
    private String topic = "REPLICA";
    private ReplicaDataDto data = new ReplicaDataDto();

    public ReplicaDto(ArrayList<Object> objects, boolean gameOver) {
        this.data.setObjects(objects);
        this.data.setGameOver(gameOver);
    }

    public void setReplicaDataDto(ArrayList<Object> objects, boolean gameOver) {
        this.data.setObjects(objects);
        this.data.setGameOver(gameOver);
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
