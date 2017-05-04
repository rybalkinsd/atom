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
    private final String point;

    public ObjectMessage(String type, int id, String point) {
        this.type = type;
        this.id = id;
        this.point = point;
    }

    @JsonCreator
    public ObjectMessage(@JsonProperty("type") String type,
                         @JsonProperty("id") int id, @JsonProperty("position") Point point) {
        this.type = type;
        this.id = id;
        this.point = point.toString();
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getPoint() {
        return point;
    }
}
