package ru.atom.bombergirl.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import ru.atom.bombergirl.gamemodel.geometry.Point;
import ru.atom.bombergirl.gamemodel.model.GameObject;

/**
 * Created by dmitriy on 02.05.17.
 */
public class ObjectMessage {
    private final String type;
    private final int id;
    private final Point position;

//    public ObjectMessage(String type, int id, Point position) {
//        this.type = type;
//        this.id = id;
//        this.position = position;
//    }

    @JsonCreator
    public ObjectMessage(@JsonProperty("type") String type,
                         @JsonProperty("id") int id,
                         @JsonProperty("position") Point position) {
        this.type = type;
        this.id = id;
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public Point getPoint() {
        return position;
    }

    @Override
    public String toString() {
        return "ObjectMessage{" +
                "type='" + type + '\'' +
                ", id=" + id +
                ", position=" + position +
                '}';
    }
}
